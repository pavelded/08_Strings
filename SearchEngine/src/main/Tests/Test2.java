import java.sql.*;

public class Test2 {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?useSSL=false", "root"
                    , "323645215");
            String idPageQuery = "SELECT id FROM page WHERE path = ?;";
            PreparedStatement idPageCheck = connection.prepareStatement(idPageQuery);
            idPageCheck.setString(1, "/");
            ResultSet page_id = idPageCheck.executeQuery();

            String idLemmaQuery = "SELECT id FROM lemma WHERE lemma = ?;";
            PreparedStatement idLemmaCheck = connection.prepareStatement(idLemmaQuery);
            idLemmaCheck.setString(1, "устройство");
            ResultSet lemma_id = idLemmaCheck.executeQuery();
            if (lemma_id.next() && page_id.next()) {
                System.out.println(lemma_id.getInt(1));
                System.out.println(page_id.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
