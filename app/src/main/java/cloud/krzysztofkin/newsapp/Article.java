package cloud.krzysztofkin.newsapp;

public class Article {
    private String webTitle;
    private String sectionName;
    private String authorName;
    private String webPublicationDate;
    private String webUrl;

    Article(String webTitle, String sectionName, String authorName, String webPublicationDate, String webUrl) {
        this.webTitle = webTitle;
        this.sectionName = sectionName;
        this.authorName = authorName;
        this.webPublicationDate = webPublicationDate;
        this.webUrl = webUrl;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getWebPublicationDate() {
        return webPublicationDate;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
