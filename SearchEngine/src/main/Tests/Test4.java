import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Test4 {
    public static void main(String[] args) {
        try {
            Document doc = Jsoup.connect("http://absra.ru")
                    .maxBodySize(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
