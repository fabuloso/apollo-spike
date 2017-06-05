import com.spotify.apollo.Environment;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import command.AddBook;
import command.CommandHandler;
import command.LendBook;
import command.LendBackBook;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spotify.apollo.Response.ok;
import static com.spotify.apollo.route.Route.sync;
import static command.Commands.addBookCommandFrom;

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
                .registerAutoRoute(sync("GET", "/lendBook", Apollo::lendBook))
                .registerAutoRoute(sync("GET", "/lendBackBook", Apollo::lendBackBook));
    }

    private static Response<ByteString> lendBook(RequestContext requestContext) {
        String name = requestContext.request().parameter("name").get();
        String title = requestContext.request().parameter("title").get();

        LendBook command = new LendBook(title, name);

        commandHandler.handle(command);

        return ok();
    }

    private static Response<ByteString> lendBackBook(RequestContext requestContext) {
        String title = requestContext.request().parameter("title").get();

        LendBackBook command = new LendBackBook(title);

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
