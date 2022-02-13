import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test3 {
    public static void main(String[] args) {
        List<String> lemmas = new ArrayList<>();
        lemmas.add("смартфон");
        lemmas.add("белый");
        lemmas.add("айсберг");
        //sr.getFrequency(map);
        //System.out.println(sr.pageIdByLemma(map));


        StringBuilder siteCode = new StringBuilder();
        try {

            List<String> lines = Files.readAllLines(Paths.get("data/110940.html"));
            for (String line : lines) {
                    if (lemmas.stream().allMatch(lemma -> line.contains(lemma))) {
                        Document doc = Jsoup.parse(line);
                        Element elements = doc.body();
                        String snippet = elements.text();
                        System.out.println(snippet);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
