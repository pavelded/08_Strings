import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main
{
    //final static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        final String searchUrl = "http://www.playback.ru/";
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Site currentSite = new Site(searchUrl);
        SiteDatabase.addSite(currentSite);
        Page rootPage = new Page(searchUrl);
        new ForkJoinPool().invoke(new PageRecursion(rootPage, sessionFactory));

        SearchRequest sr = new SearchRequest();
        sr.setText("Смартфон белый айсберг");
        List<SearchPage> foundPages = sr.getSearchPageList();
        for (SearchPage page : foundPages) {
            System.out.println("URL - " + page.getUrl());
            System.out.println("Title - " + page.getTitle());
            System.out.println("Snippet - " + page.getSnippet());
            System.out.println("Relevance - " + page.getRelevance());
            System.out.println("\n");
        }

    }
}
