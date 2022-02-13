import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name = "page")
public class Page
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(name = "path", columnDefinition = "text")
    private String path;

    @Column(name = "code")
    private int code;

    @Lob
    @Column(name = "content", length = 16777215, columnDefinition = "mediumtext")
    private String content;

    @Transient
    private String url;

    @Transient
    private Set<Page> subPages = ConcurrentHashMap.newKeySet();

    @Transient
    private HashMap<String, Integer> titleLemmas;

    @Transient
    private HashMap<String, Integer> bodyLemmas;

    @Transient
    private Map<String, Integer> resultMap;

    @Transient
    private Document doc;

    @Transient
    private String titleName;

    @Transient
    private String bodyText;

    @Column(name = "site_id")
    private int siteId;

    public Page() {
    }

    public Page(String url, int siteId) {
        this.url = url;
        this.content = null;
        this.siteId = siteId;

        String regex = "(http?|https|ftp|file)://?((W|w){3}.)?[a-zA-Z0-9]+\\.[a-zA-Z]+";
        this.path = url.replaceAll(regex, "");
    }

    public Page(String url) {
        this.url = url;
        this.content = null;
        String regex = "(http?|https|ftp|file)://?((W|w){3}.)?[a-zA-Z0-9]+\\.[a-zA-Z]+";
        this.path = url.replaceAll(regex, "");
        siteId = 1;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int id) {
        siteId = id;
    }

    public void setTitleName(String title) {
        this.titleName = title;
    }

    public void setBodyText(String body) {
        this.bodyText = body;
    }

    public String getPath() {
        return this.path;
    }

    public void addCode(int code) {
        this.code = code;
    }

    public void addContent(Document doc) {
        this.doc = doc;
        this.content = doc.html();
    }

    public String getContent() {
        return content;
    }

    public Set<Page> getSubPages() {
        return subPages;
    }

    public synchronized void addSubPage(Page subPage) {
        subPages.add(subPage);
    }

    public String getUrl() {
        return url;
    }

    public synchronized void setTitleName() {
        Elements titleElements = doc.select("head > title");
        titleName = titleElements.text();
    }

    public synchronized String getTitleName() {
        return titleName;
    }

    public synchronized void setBodyText() {
        Elements bodyElements = doc.select("*");
        StringBuilder sBuilder = new StringBuilder();
        for (Element element : bodyElements) {
            if (element.hasText() && !element.className().equals("") && !element.className().contains("body")) {
                sBuilder.append(element.text() + "\n");
            }
        }
        bodyText = sBuilder.toString();
    }

    public synchronized void setTitleLemmas() {
        titleLemmas = Morph.getLemmasMap(titleName);
    }

    public synchronized void setBodyLemmas() {
        bodyLemmas = Morph.getLemmasMap(bodyText);
    }

    public synchronized void insertInSearchTable() {
        resultMap = getResultLemmasMap(titleLemmas, bodyLemmas);
        if (!resultMap.isEmpty()) {
            try {
                Set<String> keys = resultMap.keySet();
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?useSSL=false", "root"
                        , "323645215");

                String idPageQuery = "SELECT id FROM page WHERE path = ? AND site_id = ?;";
                PreparedStatement idPageCheck = connection.prepareStatement(idPageQuery);
                idPageCheck.setString(1, getPath());
                idPageCheck.setInt(2, getSiteId());
                ResultSet page_id = idPageCheck.executeQuery();

                if (page_id.next()) {
                    for (String key : keys) {

                        String idLemmaQuery = "SELECT id FROM lemma WHERE lemma = ? AND site_id = ?;";
                        PreparedStatement idLemmaCheck = connection.prepareStatement(idLemmaQuery);
                        idLemmaCheck.setString(1, key);
                        idLemmaCheck.setInt(2, getSiteId());
                        ResultSet lemma_id = idLemmaCheck.executeQuery();

                        if (lemma_id.next()) {
                            if (titleLemmas.containsKey(key)) {
                                float titleRank = (float) (titleLemmas.get(key) * 1);
                                float totalRank = titleRank + (float) ( bodyLemmas.get(key) * 0.8);

                                String querySave = "INSERT INTO search (page_id, lemma_id, score) VALUES (?, ?, ?);";
                                final PreparedStatement save = connection.prepareStatement(querySave);
                                save.setInt(1, page_id.getInt(1));
                                save.setInt(2, lemma_id.getInt(1));
                                save.setFloat(3, totalRank);
                                save.execute();
                            } else {
                                float totalRank = (float) resultMap.get(key) * (float) 0.8;
                                String querySave = "INSERT INTO search (page_id, lemma_id, score) VALUES (?, ?, ?);";
                                final PreparedStatement save = connection.prepareStatement(querySave);
                                save.setInt(1, page_id.getInt(1));
                                save.setInt(2, lemma_id.getInt(1));
                                save.setFloat(3, totalRank);
                                save.execute();
                            }
                        }
                    }
                }
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized void insertInLemmaTable() {
        resultMap = getResultLemmasMap(titleLemmas, bodyLemmas);
        if (!resultMap.isEmpty()) {
            try {
                Set<String> keys = resultMap.keySet();
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?useSSL=false", "root"
                        , "323645215");

                for (String key : keys) {
                    Lemma lemma = new Lemma(key);
                    String queryCheck = "SELECT frequency from lemma WHERE lemma = ? AND site_id = ?";
                    final PreparedStatement check = connection.prepareStatement(queryCheck);
                    check.setString(1, key);
                    check.setInt(2, getSiteId());
                    final ResultSet resultSet = check.executeQuery();

                    if(!resultSet.next()) {
                        String querySave = "INSERT INTO lemma (lemma, frequency, site_id) VALUES (?, ?, ?);";
                        final PreparedStatement save = connection.prepareStatement(querySave);
                        save.setString(1, key);
                        save.setInt(2, 1);
                        save.setInt(3, getSiteId());
                        save.execute();
                    } else {
                        lemma.setFrequency(resultSet.getInt(1) + 1);
                        String querySave = "UPDATE lemma SET lemma = ?, frequency = ? WHERE lemma = ? AND site_id = ?;";
                        final PreparedStatement save = connection.prepareStatement(querySave);
                        save.setString(1, key);
                        save.setInt(2, lemma.getFrequency());
                        save.setString(3, key);
                        save.setInt(4, getSiteId());
                        save.execute();
                    }
                }
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public synchronized Map<String, Integer> getResultLemmasMap(Map<String, Integer> titleMap, Map<String, Integer> bodyMap) {
        Map<String,Integer> resultMap = new HashMap<>();
        Set<String> intersectionKeys = getIntersections(titleMap, bodyMap);
        if (!intersectionKeys.isEmpty() && intersectionKeys != null) {
            for (String key : intersectionKeys) {
                Integer totalValue = bodyMap.get(key) + titleMap.get(key);
                resultMap.put(key, totalValue);
            }
            for (String key : bodyMap.keySet()) {
                if (!titleMap.containsKey(key)) {
                    resultMap.put(key, bodyMap.get(key));
                }
            }
        } else {
            resultMap.putAll(titleMap);
            resultMap.putAll(bodyMap);
        }
        return resultMap;
    }

    public synchronized Set<String> getIntersections(Map<String, Integer> titleMap, Map<String, Integer> bodyMap) {
        Map<String,Integer> intersectionsToRetain = new HashMap<>(bodyMap);
        intersectionsToRetain.keySet().retainAll(titleMap.keySet());
        Set<String> intersectionsKeys = intersectionsToRetain.keySet();
        return intersectionsKeys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return url.equals(page.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
