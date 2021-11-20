package serach;

import java.util.*;

public class CoolNumbers {

    public static List<String> generateCoolNumbers() {

        ArrayList<String> coolNumbersList = new ArrayList<>();
        ArrayList<String> coolNumbersListWithoutRegion = new ArrayList<>();

        String numberplate = "";
        String firstString = "";
        String secondString = "";
        String thirdString = "";
        String fourthString = "";
        String fifthhString = "";
        String sixthhString = "";
        String region = "";

        ArrayList<String> allowedLetters = new ArrayList<>() {{
            add("А");
            add("В");
            add("Е");
            add("К");
            add("М");
            add("Н");
            add("О");
            add("Р");
            add("С");
            add("Т");
            add("У");
            add("Х");
        }};

        for (int i = 0; i < allowedLetters.size(); i++) {
            firstString = allowedLetters.get(i);
            for (int j = 1; j < 10; j++) {
                secondString = Integer.toString(j);
                thirdString = Integer.toString(j);
                fourthString = Integer.toString(j);
                for (int k = 0; k < allowedLetters.size(); k++) {
                    fifthhString = allowedLetters.get(k);
                    for (int l = 0; l < allowedLetters.size(); l++) {
                        sixthhString = allowedLetters.get(l);
                        for (int p = 1; p <= 199; p++) {
                            if (p < 10) {
                                region = Integer.toString(0);
                                region += Integer.toString(p);
                                coolNumbersList.add(firstString + secondString + thirdString + fourthString + fifthhString + sixthhString + region);
                            } else {
                                region = Integer.toString(p);
                                coolNumbersList.add(firstString + secondString + thirdString + fourthString + fifthhString + sixthhString + region);
                            }
                        }
                    }
                }
            }
        }
        return coolNumbersList;
    }

    public static boolean bruteForceSearchInList(List<String> list, String number) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == number) {
                return true;
            }
        }
        return false;
    }

    public static boolean binarySearchInList(List<String> sortedList, String number) {
        int index = Collections.binarySearch(sortedList, number);
        if (index >= 0) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean searchInHashSet(HashSet<String> hashSet, String number) {
        if (hashSet.contains(number)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean searchInTreeSet(TreeSet<String> treeSet, String number) {
        long start = System.currentTimeMillis();
        if (treeSet.contains(number)) {
            return true;
        } else {
            return false;
        }
    }

}
