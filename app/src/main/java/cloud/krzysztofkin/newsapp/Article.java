package cloud.krzysztofkin.newsapp;

public class Article {
    String webTitle;
    String sectionName;
    String authorName;
    String webPublicationDate;
    String webUrl;

    public Article(String webTitle, String sectionName, String authorName, String webPublicationDate, String webUrl) {
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
