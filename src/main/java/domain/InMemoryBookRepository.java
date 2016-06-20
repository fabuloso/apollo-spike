package domain;

import domain.event.Event;
import infrastructure.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.size;

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

        if(isEmpty(bookEvents)) {
            LOG.info("No events found for the book called: {}", title);
            return null;
        }

        LOG.info("Found #{} events for book {}", size(bookEvents), title);

        return book.from(bookEvents);
    }
}
