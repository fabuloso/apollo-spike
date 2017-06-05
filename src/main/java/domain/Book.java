package domain;

import domain.event.BookAdded;
import domain.event.BookLent;
import domain.event.BookLentBack;
import domain.event.Event;
import infrastructure.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private EventStore eventStore;
    private List<Event> events = new ArrayList<>();
    private String title;
    private Year year;
    private String user;
    private Boolean lent = false;

    final static Logger LOG = LoggerFactory.getLogger(Book.class);

    public Book() {
        eventStore = EventStore.getInstance();
    }

    public void create(String title, Year year) {
        BookAdded event = new BookAdded(title, year);
        eventStore.save(event);
        applyEvent(event);
    }

    public void lend(String title, String name) {
        BookLent event = new BookLent(title, name);
        if (this.isPresent()) {
            eventStore.save(event);
            applyEvent(event);
        } else {
            LOG.warn("The book is already lent to {}", user);
        }
    }


    public void lendBack() {
        BookLentBack event = new BookLentBack(this.title);
        eventStore.save(event);
        applyEvent(event);
    }

    private void applyEvent(Event event) {
        events.add(event);
        event.applyOn(this);
    }

    protected Book from(List<Event> bookEvents) {
        bookEvents.forEach(this::applyEvent);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public void lentTo(String name) {
        this.user = name;
        this.lent = true;
    }

    public void lentBack() {
        this.user = "";
        this.lent = false;
    }

    public boolean isPresent() {
        return !lent;
    }

}
