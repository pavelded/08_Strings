import java.util.ArrayList;
import java.util.List;

public class LinksDatabase {

    private static List<String> links = new ArrayList<>();

    public static synchronized void addLink(String link) {
        links.add(link);
    }

    public static synchronized boolean containsLink(String link) {
        return links.contains(link);
    }
}
