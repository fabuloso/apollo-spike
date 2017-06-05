package command;

import domain.Book;
import domain.BookRepository;
import domain.InMemoryBookRepository;

public class CommandHandler {

    private BookRepository repository;

    public CommandHandler() {
        this.repository = new InMemoryBookRepository();
    }

    public void handle(AddBook command) {
        Book book = new Book();

        book.create(command.getTitle(), command.getYear());
    }

    public void handle(LendBook command) {
        Book book = repository.findBy(command.getTitle());
        book.lend(command.getTitle(), command.getName());
    }

    public void handle(LendBackBook command) {
        Book book = repository.findBy(command.getTitle());

        book.lendBack();
    }
}
