package domain.event;

import domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;

public class BookAdded implements Event {

    final static Logger LOG = LoggerFactory.getLogger(BookAdded.class);

    private Year year;
    private String title;

    public BookAdded(String title, Year year) {
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void applyOn(Book book) {
        LOG.info("Apply event: " + this);

        book.setTitle(title);
        book.setYear(year);
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }

}
