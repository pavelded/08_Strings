import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Movements {

    private static String movementList;

    public Movements(String pathMovementsCsv) {
        movementList = pathMovementsCsv;
    }

    public double getExpenseSum() {
        double expenseSum = 0;
        try {
            List<String> dataList = Files.readAllLines(Paths.get(movementList));
            for (int i = 0; i < dataList.size(); i++) {
                if (i == 0) {
                } else {
                    String[] fragments = dataList.get(i).split(",");
                    System.out.println(Arrays.toString(fragments));
                    System.out.println(fragments.length);
                    if (fragments.length == 10 && fragments[8].contains("\"")) {
                        String sum = (fragments[8] + "." + fragments[9]).replaceAll("\"", "");
                        expenseSum += Double.parseDouble(sum);
                    } else if (fragments.length == 9 && fragments[8].contains("\"")) {
                        String sum = (fragments[7] + "." + fragments[8]).replaceAll("\"", "");
                        expenseSum += Double.parseDouble(sum);
                    } else if (fragments.length == 8) {
                        expenseSum += Double.parseDouble(fragments[7]);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return expenseSum;
    }

    public double getIncomeSum() {
        double incomeSum = 0;
        try {
            List<String> dataList = Files.readAllLines(Paths.get(movementList));
            for (int i = 0; i < dataList.size(); i++) {
                if (i == 0) {
                } else {
                    String[] fragments = dataList.get(i).split(",");
                    if (fragments[6].contains("\"")) {
                        String sum = (fragments[6] + "." + fragments[7]).replaceAll("\"", "");
                        incomeSum += Double.parseDouble(sum);
                    } else {
                        incomeSum += Double.parseDouble(fragments[6]);
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return incomeSum;
    }
}
