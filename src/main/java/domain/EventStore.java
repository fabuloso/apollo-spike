package domain;

public interface EventStore {

    void save(Event event);
}
