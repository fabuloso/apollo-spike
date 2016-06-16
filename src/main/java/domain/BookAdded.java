package domain;

import java.time.Year;

public class BookAdded extends Event {
    public Year year;
    public String title;

    public BookAdded(Integer id, String title, Year year) {
        super(id);
        this.title = title;
        this.year = year;
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
