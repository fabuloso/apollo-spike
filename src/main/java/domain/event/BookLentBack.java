package domain.event;

import domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookLentBack implements Event{
    final static Logger LOG = LoggerFactory.getLogger(BookLentBack.class);

    private String title;

    public BookLentBack(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void applyOn(Book book) {
        LOG.info("Apply event: " + this);
        book.lentBack();
    }
}
