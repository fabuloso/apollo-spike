package commands;

import domain.Book;
import domain.BookRepository;

public class CommandHandler {

    private BookRepository repository;

    public void handle(AddBook command) {
        Book book = new Book();

        book.create(command.getTitle(), command.getYear());
    }

    public void handle(RateBook rate) {
        Book book = repository.findBy(rate.getTitle());

        book.rate(rate.getTitle(), rate.getStars(), rate.getComment())
    }

}
