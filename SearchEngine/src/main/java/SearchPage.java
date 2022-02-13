public class SearchPage implements Comparable<SearchPage> {
    private String url;
    private String title;
    private String snippet;
    private int relevance = 0;

    public SearchPage() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public int getRelevance() {
        return relevance;
    }

    public void increaseRelevance(int relevance) {
        this.relevance += relevance;
    }

    @Override
    public int compareTo(SearchPage o) {
        return o.getRelevance() - this.getRelevance();
    }
}
