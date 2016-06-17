package domain;

import domain.event.BookAdded;
import domain.event.BookRated;
import domain.event.Event;
import infrastructure.EventStore;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private EventStore eventStore;
    private List<Event> events = new ArrayList<>();
    private String title;
    private Year year;
    private List<Rate> rates = new ArrayList<>();

    public Book() {
        eventStore = EventStore.getInstance();
    }

    public void create(String title, Year year) {
        BookAdded event = new BookAdded(title, year);
        eventStore.save(event);
        applyEvent(event);
    }

    public void rate(String title, Integer stars, String comment) {
        BookRated event = new BookRated(title, stars, comment);
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

    public void addRate(Integer stars, String comment) {
        rates.add(new Rate(stars, comment));
    }
}
