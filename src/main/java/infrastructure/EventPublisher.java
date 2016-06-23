package infrastructure;

import domain.event.Event;

import javax.jms.JMSException;

public interface EventPublisher {

    void push(Event event) throws JMSException;

}
