package domain;

import infrastructure.BookEventStore;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Book {

    private EventStore eventStore;
    private List<Event> events = new ArrayList<>();

    public Book() {
        this(new BookEventStore());
    }

    public Book(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void create(Integer id, String title, Year year) {
        BookAdded event = new BookAdded(id, title, year);
        eventStore.save(event);
        applyEvent(event);
    }

    private void applyEvent(BookAdded event) {
        events.add(event);
    }

}
