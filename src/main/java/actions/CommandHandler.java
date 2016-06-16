package actions;

import domain.Book;

public class CommandHandler {

    public void handle(AddBook command) {
        Book book = new Book();

        book.create(1, command.getTitle(), command.getYear());
    }

}
