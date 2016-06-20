package infrastructure;

import domain.event.Event;

public interface EventPublisher {

    void push(Event event);

}
