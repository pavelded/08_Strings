import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;

public class PageRecursion extends RecursiveAction {

    private final Page page;
    private static String SITE_URL;
    private final SessionFactory sessionFactory;
    private Site currentSite;


    public PageRecursion(Page page, SessionFactory sessionFactory) {
        this.page = page;
        this.sessionFactory = sessionFactory;
    }

    @Override
    protected void compute() {
        currentSite = SiteDatabase.getSite(page.getUrl());
        if (page.getPath().equals("/")) {
            page.setSiteId(currentSite.getId());
        }

        currentSite.setStatus(Status.INDEXING);
        currentSite.setStatusTime(new Date());
        LinksDatabase.addLink(page.getUrl());

        try {
            org.jsoup.Connection.Response response = Jsoup.connect(page.getUrl())
                    .execute();
            page.addCode(response.statusCode());

            Document doc = Jsoup.connect(page.getUrl())
                    .maxBodySize(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .timeout(10000).get();
            page.addContent(doc);

        } catch (Exception ex) {
            currentSite.setStatus(Status.FAILED);
            currentSite.setStatusTime(new Date());
            currentSite.setLastError(ex.getMessage());
            ex.printStackTrace();
        }

            try (Session session = sessionFactory.openSession()) {

                if (page.getPath().equals("/")) {
                    SITE_URL = page.getUrl();
                    session.save(page);
                    session.close();
                    currentSite.setStatusTime(new Date());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            page.setTitleName();
            if (page.getPath().equals("/")) {
                currentSite.setName(page.getTitleName());
            }
            page.setBodyText();
            page.setTitleLemmas();
            page.setBodyLemmas();
            page.insertInLemmaTable();

            if (page.getPath().equals("/")) {
                page.insertInSearchTable();
                currentSite.setStatusTime(new Date());
            }

            Set<Page> childrenLinks = this.getChildrenLinks(page);
            Set<PageRecursion> taskList = new HashSet<>();
            for (Page child : childrenLinks) {
                if (child != null)
                taskList.add((PageRecursion) new PageRecursion(child, sessionFactory).fork());
            }

            for (PageRecursion task : taskList) {
                task.join();
                try (Session session = sessionFactory.openSession()) {
                    session.save(task.getPage());
                    task.getPage().insertInSearchTable();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            try (Session session = sessionFactory.openSession()) {
                currentSite.setStatus(Status.INDEXED);
                currentSite.setStatusTime(new Date());
                if (page.getPath().equals("/")) {
                    session.save(currentSite);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    private Page getPage() {
        return page;
    }

    private synchronized Set<Page> getChildrenLinks(Page parent) {
        try {
            Document doc = Jsoup.connect(parent.getUrl())
                    .maxBodySize(0)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:80.0) Gecko/20100101 Firefox/80.0")
                    .timeout(10000).get();
            Elements links = doc.select("a[href]");
            Set<String> absUrls = links.stream().map(el -> el.attr("abs:href"))
                    .filter(u -> !u.equals(parent.getUrl()))
                    .filter(y -> y.startsWith(SITE_URL))
                    .filter(v -> !v.contains("#") && !v.contains("?") && !v.contains("'"))
                    .filter(w -> !w.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|pdf))$)"))
                    .collect(Collectors.toSet());
            for (String link : absUrls) {
                Page page = new Page(link, currentSite.getId());
                if (!LinksDatabase.containsLink(link)) {
                    parent.addSubPage(page);
                    LinksDatabase.addLink(link);
                    System.out.println(link);
                }
                }
            }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return parent.getSubPages();
    }
}
