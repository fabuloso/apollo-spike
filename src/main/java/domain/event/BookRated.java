package domain.event;

import domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class BookRated implements Event {

    final static Logger LOG = LoggerFactory.getLogger(BookRated.class);

    private final String title;
    private final Integer stars;
    private final String comment;

    public BookRated(String title, Integer stars, String comment) {
        this.title = title;
        this.stars = stars;
        this.comment = comment;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void applyOn(Book book) {
        LOG.info("Apply event: " + this);

        book.addRate(stars, comment);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
