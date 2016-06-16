package commands;

public class RateBook {

    private String title;
    private String comment;
    private Integer stars;

    public RateBook(String title, String comment, Integer stars) {
        this.title = title;
        this.comment = comment;
        this.stars = stars;
    }

    public String getTitle() {
        return title;
    }

    public Integer getStars() {
        return stars;
    }

    public String getComment() {
        return comment;
    }
}
