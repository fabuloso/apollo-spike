package domain;

import domain.event.Event;
import infrastructure.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class InMemoryBookRepository implements BookRepository {

    private final EventStore store;

    final static Logger LOG = LoggerFactory.getLogger(InMemoryBookRepository.class);

    public InMemoryBookRepository() {
        store = EventStore.getInstance();
    }

    @Override
    public Book findBy(String title) {
        Book book = new Book();
        List<Event> bookEvents =
                store.getEvents().stream()
                        .filter(e -> e.getTitle().equals(title)).collect(toList());

        LOG.info("Found #{} events for book {}", bookEvents.size(), bookEvents.get(0).getTitle());

        book.setTitle(title);
        return book.from(bookEvents);
    }
}
