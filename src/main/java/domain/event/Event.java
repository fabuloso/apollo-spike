package domain.event;

import domain.Book;

import java.io.Serializable;

public interface Event extends Serializable{

    String getTitle();

    void applyOn(Book book);
}
