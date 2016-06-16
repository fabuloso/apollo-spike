package infrastructure;

import domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


public class EventStore {

    final static Logger LOG = LoggerFactory.getLogger(EventStore.class);

    private final List<Event> events;
    private static EventStore instance;

    private EventStore(List<Event> events) {
        this.events = events;
    }

    public static EventStore getInstance() {
        if (instance == null) {
            instance = new EventStore(new ArrayList<>());
        }
        return instance;
    }

    public void save(Event event) {
        LOG.info("Save Event");
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }
}