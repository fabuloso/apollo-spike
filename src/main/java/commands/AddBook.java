package commands;

import java.time.Year;

public class AddBook {

    public String title;
    public Year year;

    public AddBook(String title, String year) {
        this.title = title;
        this.year = Year.parse(year);
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
