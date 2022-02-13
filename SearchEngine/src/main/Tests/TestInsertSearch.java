import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Set;

public class TestInsertSearch {

    public static void main(String[] args) {
        try {
            Page page = new Page("/", 1);
            String key = "магазин";
            HashMap<String, Integer> titleLemmas = new HashMap<>();
            titleLemmas.put("интернет", 1);

            HashMap<String, Integer> resultMap = new HashMap<>();
            resultMap.put("магазин", 1);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?useSSL=false", "root"
                    , "323645215");

                String idPageQuery = "SELECT id from page WHERE path = ?";
                final PreparedStatement idPageCheck = connection.prepareStatement(idPageQuery);
                idPageCheck.setString(1, page.getPath());
                final ResultSet page_id = idPageCheck.executeQuery();

                String idLemmaQuery = "SELECT id from lemma WHERE lemma = ?";
                final PreparedStatement idLemmaCheck = connection.prepareStatement(idLemmaQuery);
                idLemmaCheck.setString(1, key);
                final ResultSet lemma_id = idLemmaCheck.executeQuery();

                if (titleLemmas.containsKey(key) && page_id.next() && lemma_id.next()) {
                    float rank = (float) titleLemmas.get(key) * 1;
                    rank += (float) resultMap.get(key) * 0.8;

                    String querySave = "INSERT INTO search (page_id, lemma_id, score) VALUES (?, ?, ?);";
                    final PreparedStatement save = connection.prepareStatement(querySave);
                    save.setInt(1, page_id.getInt(1));
                    save.setInt(2, lemma_id.getInt(1));
                    save.setFloat(3, rank);
                    save.execute();
                } else if (page_id.next() && lemma_id.next()) {
                    float rank = (float) resultMap.get(key) * (float) 0.8;

                    String querySave = "INSERT INTO search (page_id, lemma_id, score) VALUES (?, ?, ?);";
                    final PreparedStatement save = connection.prepareStatement(querySave);
                    save.setInt(1, page_id.getInt(1));
                    save.setInt(2, lemma_id.getInt(1));
                    save.setFloat(3, rank);
                    save.execute();
                }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
