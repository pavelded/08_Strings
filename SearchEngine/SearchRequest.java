import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SearchRequest {

    private String text;
    private Map<String, Integer> searchLemmas;
    private int Rabs;
    private int Rrel;

    StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
    Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
    SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
    Session session = sessionFactory.openSession();


    public SearchRequest() {
    }

    public List<SearchPage> getResultPages(Map<String, Integer> map) {
        List<SearchPage> searchPages = new ArrayList<>();

        List<Integer> firstPageIds = firstPageIdsForLemma(map);
        Set<Integer> resultPageIds = resultPageIdsForLemma(firstPageIds, map);

        for (Integer id : resultPageIds) {
            SearchPage searchPage = new SearchPage();
            List<String> lemmas = new ArrayList<>();
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?allowPublicKeyRetrieval=true&useSSL=false", "root"
                        , "323645215");
                for (String lemma : map.keySet()) {
                    String lemmaIdQuery = "SELECT id FROM lemma WHERE lemma = ? AND site_id = ?";
                    PreparedStatement lemmaIdCheck = connection.prepareStatement(lemmaIdQuery);
                    lemmaIdCheck.setString(1, lemma);
                    ResultSet lemmaId = lemmaIdCheck.executeQuery();
                    if (lemmaId.next()) {
                        String scoreQuery = "SELECT score FROM search WHERE page_id = ? AND lemma_id = ?";
                        PreparedStatement scoreCheck = connection.prepareStatement(scoreQuery);
                        scoreCheck.setInt(1, id);
                        scoreCheck.setInt(2, lemmaId.getInt(1));
                        ResultSet score = scoreCheck.executeQuery();
                        if (score.next()) {
                            searchPage.increaseRelevance(score.getInt(1));
                        }
                    }
                    lemmas.add(lemma);
                }

                Page page = session.get(Page.class, id);
                searchPage.setUrl(page.getPath());

                Pattern title = Pattern.compile("<title>.+</title>");
                Matcher titleMatcher = title.matcher(page.getContent());
                if (titleMatcher.find()) {
                    searchPage.setTitle(page.getContent().substring(titleMatcher.start(), titleMatcher.end())
                            .replaceAll("<(/)?title>", ""));
                }

                String[] lines = page.getContent().split("\\\\r?\\\\n");
                for (String line : lines) {
                    if (lemmas.stream().allMatch(lemma -> line.contains(lemma))) {
                        Document doc = Jsoup.parse(line);
                        Element snippet = doc.body();
                        searchPage.setSnippet(snippet.text());
                    }
                }
                searchPages.add(searchPage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Collections.sort(searchPages);
        return searchPages;
    }

    public Set<Integer> resultPageIdsForLemma(List<Integer> list, Map<String, Integer> map) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?allowPublicKeyRetrieval=true&useSSL=false", "root"
                    , "323645215");
            for (String lemma : map.keySet()) {
                List<Integer> pageIds = new ArrayList<>();
                String lemmaIdQuery = "SELECT id FROM lemma WHERE lemma = ?";
                PreparedStatement lemmaIdCheck = connection.prepareStatement(lemmaIdQuery);
                lemmaIdCheck.setString(1, lemma);
                ResultSet lemmaId = lemmaIdCheck.executeQuery();
                if (lemmaId.next()) {
                    String pageIdQuery = "SELECT page_id FROM search WHERE lemma_id = ?";
                    PreparedStatement pageIdCheck = connection.prepareStatement(pageIdQuery);
                    pageIdCheck.setInt(1, lemmaId.getInt(1));
                    ResultSet pageId = pageIdCheck.executeQuery();
                    while (pageId.next()) {
                        pageIds.add(pageId.getInt(1));
                    }
                    list.retainAll(pageIds);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list.stream().collect(Collectors.toSet());
    }

    public List<Integer> firstPageIdsForLemma(Map<String, Integer> map) {
        List<Integer> pageIds = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?allowPublicKeyRetrieval=true&useSSL=false", "root"
                    , "323645215");
            for (String lemma : map.keySet()) {
                String lemmaIdQuery = "SELECT id FROM lemma WHERE lemma = ?";
                PreparedStatement lemmaIdCheck = connection.prepareStatement(lemmaIdQuery);
                lemmaIdCheck.setString(1, lemma);
                ResultSet lemmaId = lemmaIdCheck.executeQuery();
                if (lemmaId.next()) {
                    String pageIdQuery = "SELECT page_id FROM search WHERE lemma_id = ?";
                    PreparedStatement pageIdCheck = connection.prepareStatement(pageIdQuery);
                    pageIdCheck.setInt(1, lemmaId.getInt(1));
                    ResultSet pageId = pageIdCheck.executeQuery();
                    while (pageId.next()) {
                        pageIds.add(pageId.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageIds;
    }

    public List<SearchPage> getSearchPageList() {
        searchLemmas = Morph.getSearchingLemmasMap(text);
        getFrequency(searchLemmas);
        Map<String, Integer> sortedLemmasMap = valueSort(searchLemmas);
        return getResultPages(sortedLemmasMap);
    }

    public void getFrequency(Map<String, Integer> map) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?allowPublicKeyRetrieval=true&useSSL=false", "root"
                    , "323645215");

            for (String key : map.keySet()) {
                String queryCheck = "SELECT frequency FROM lemma WHERE lemma = ?";
                final PreparedStatement check = connection.prepareStatement(queryCheck);
                check.setString(1, key);
                final ResultSet resultSet = check.executeQuery();

                if (resultSet.next()) {
                    map.computeIfPresent(key, (k, v) -> {
                        try {
                            return v + resultSet.getInt(1);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        return v;
                    });
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static <K, V extends Comparable<V>> Map<K, V> valueSort(final Map<K, V> map)
    {
        Comparator<K> valueComparator = (k1, k2) -> {
            int comp = map.get(k1).compareTo(
                    map.get(k2));
            if (comp == 0)
                return 1;
            else
                return comp;
        };
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);
        sorted.putAll(map);
        return sorted;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Integer> getSearchLemmas() {
        return searchLemmas;
    }

    public void setSearchLemmas(Map<String, Integer> searchLemmas) {
        this.searchLemmas = searchLemmas;
    }

    public int getRabs() {
        return Rabs;
    }

    public void setRabs(int rabs) {
        Rabs = rabs;
    }

    public int getRrel() {
        return Rrel;
    }

    public void setRrel(int rrel) {
        Rrel = rrel;
    }
}
