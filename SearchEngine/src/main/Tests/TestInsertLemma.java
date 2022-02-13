import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;

public class TestInsertLemma {
    public static void main(String[] args) {
                try {
                    String key = "залупа";
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/search_engine?useSSL=false", "root"
                            , "323645215");

                        Lemma lemma = new Lemma(key);
                        String queryCheck = "SELECT frequency from lemma WHERE lemma = ?";
                        final PreparedStatement check = connection.prepareStatement(queryCheck);
                        check.setString(1, key);
                        final ResultSet resultSet = check.executeQuery();

                        if(!resultSet.next()) {
                            String querySave = "INSERT INTO lemma (lemma, frequency) VALUES (?, ?);";
                            final PreparedStatement save = connection.prepareStatement(querySave);
                            save.setString(1, key);
                            save.setInt(2, 1);
                            save.execute();
                        } else {
                            lemma.setFrequency(resultSet.getInt(1) + 1);
                            String querySave = "UPDATE lemma SET lemma = ?, frequency = ? WHERE lemma = ?;";
                            final PreparedStatement save = connection.prepareStatement(querySave);
                            save.setString(1, key);
                            save.setInt(2, lemma.getFrequency());
                            save.setString(3, key);
                            save.execute();
                        }
                    connection.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
    }
