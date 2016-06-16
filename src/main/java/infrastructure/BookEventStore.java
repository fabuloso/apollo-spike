package infrastructure;

import domain.Event;
import domain.EventStore;

import java.util.ArrayList;
import java.util.List;


public class BookEventStore implements EventStore{

    private final List<Event> events;

    public BookEventStore() {
        events = new ArrayList<>();
    }

    @Override
    public void save(Event event) {
        events.add(event);
    }
}
