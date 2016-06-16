package domain;


import infrastructure.EventStore;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class InMemoryBookRepository implements BookRepository {

    private final EventStore store;

    public InMemoryBookRepository() {
        store = EventStore.getInstance();
    }

    @Override
    public Book findBy(String title) {
        Book book = new Book();
        List<Event> bookEvents =
                store.getEvents().stream()
                        .filter(e -> e.getTitle().equals(title)).collect(toList());

        return book.from(bookEvents);
    }
}
