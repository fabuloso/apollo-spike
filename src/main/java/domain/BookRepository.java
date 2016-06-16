package domain;

public interface BookRepository {

    Book findBy(String title);
}
