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

    public void handle(RateBook rate) {
        Book book = repository.findBy(rate.getTitle());
        if (book != null) {
            book.rate(rate.getTitle(), rate.getStars(), rate.getComment());
        }
    }

}
