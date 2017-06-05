package command;

public class LendBook {
    private final String title;
    private final String name;

    public LendBook(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
