package domain.event;

import domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookLent implements Event {

    final static Logger LOG = LoggerFactory.getLogger(BookLent.class);

    private final String title;
    private final String user;

    public BookLent(String title, String name) {
        this.title = title;
        this.user = name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void applyOn(Book book) {
        LOG.info("Apply event: " + this);
        book.lentTo(user);
    }
}
