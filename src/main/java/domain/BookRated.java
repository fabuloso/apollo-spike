package domain;

public class BookRated extends Event {

    private final String title;
    private final Integer stars;
    private final String comment;

    public BookRated(String title, Integer stars, String comment) {
        super(1);
        this.title = title;
        this.stars = stars;
        this.comment = comment;
    }
}
