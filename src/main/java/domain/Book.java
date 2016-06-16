package domain;

import infrastructure.EventStore;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private EventStore eventStore;
    private List<Event> events = new ArrayList<>();

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
    }

    public void from(List<Event> bookEvents) {
        bookEvents.forEach(this::applyEvent);
    }
}
