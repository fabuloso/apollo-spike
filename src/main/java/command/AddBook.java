package command;

import java.time.Year;

public class AddBook {

    private String title;
    private Year year;

    public AddBook(String title, String year) {
        this.title = title;
        this.year = Year.parse(year);
    }

    public Year getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }
}
