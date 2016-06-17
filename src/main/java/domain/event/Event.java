package domain.event;

import domain.Book;

public interface Event {

    String getTitle();

    void applyOn(Book book);
}
