import java.util.ArrayList;
import java.util.List;

public class SiteDatabase {
    private static List<Site> siteList = new ArrayList<>();

    public synchronized static void addSite(Site site) {
        siteList.add(site);
    }

    public synchronized static Site getSite(String url) {
        for (Site site : siteList) {
            if (url.contains(site.getUrl())) {
                return site;
            }
        }
        return null;
    }
}
