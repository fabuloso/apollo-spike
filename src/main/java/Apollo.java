import com.spotify.apollo.Environment;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import command.AddBook;
import command.CommandHandler;
import command.RateBook;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spotify.apollo.Response.ok;
import static com.spotify.apollo.route.Route.sync;
import static command.Commands.addBookCommandFrom;
import static command.Commands.rateBookCommandFrom;

public class Apollo {

    private static MongoDbRepository repository;
    private static CommandHandler commandHandler;

    final static Logger LOG = LoggerFactory.getLogger(Apollo.class);


    public static void main(String[] args) throws LoadingException {
        commandHandler = new CommandHandler();
        LOG.info("INIT MONGO");
        repository = new MongoDbRepository();
        LOG.info("SEED MONGO");
        seed(repository);
        LOG.info("INIT ROUTES");
        HttpService.boot(Apollo::init, "apollo", args);
    }

    static void init(Environment environment) {
        environment.routingEngine()
                .registerAutoRoute(sync("GET", "/addBook", Apollo::addBook))
                .registerAutoRoute(sync("GET", "/rateBook", Apollo::rateBook));
    }

    private static Response<ByteString> rateBook(RequestContext context) {
        RateBook command = rateBookCommandFrom(context);

        LOG.info("Fire command: " + command);
        commandHandler.handle(command);

        return ok();
    }

    private static Response<ByteString> addBook(RequestContext context) {
        AddBook command = addBookCommandFrom(context);

        LOG.info("Fire command: " + command);
        commandHandler.handle(command);

        return ok();
    }

    private static void seed(MongoDbRepository repository) {
        repository.drop();
        for (int i = 0; i < 10; i++) {
            String json = jsonFor(i);
            repository.insert(json);
        }
    }

    private static String jsonFor(int i) {
        return "{\"title\":\"a title\",\"author\":{\"name\":\"" + "name " + i + "\"}}";
    }
}
