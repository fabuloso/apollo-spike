package infrastructure;

import com.google.gson.Gson;
import domain.event.Event;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class BookEventPublisher implements EventPublisher {

    final static Logger LOG = LoggerFactory.getLogger(EventStore.class);

    @Override
    public void push(Event event) {
        try {
            LOG.info("Pushing Event on queue");
            ConnectionFactory factory = new ActiveMQConnectionFactory("tcp://queue:61616");
            Connection connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic topic = session.createTopic("events");

            MessageProducer producer = session.createProducer(topic);
            TextMessage msg = session.createTextMessage(new Gson().toJson(event));
            producer.send(msg);
            LOG.info("Event Pushed");

        } catch (Exception e ) {
            LOG.error("An Exception: ", e);
            e.printStackTrace();
        }
    }

}
