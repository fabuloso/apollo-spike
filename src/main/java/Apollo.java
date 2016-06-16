import actions.AddBook;
import actions.CommandHandler;
import com.spotify.apollo.Environment;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static com.spotify.apollo.Response.ok;
import static com.spotify.apollo.route.Route.sync;

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
                .registerAutoRoute(sync("GET", "/ping", context -> "pong"));
    }

    private static Response<ByteString> addBook(RequestContext requestContext) {
        Map<String, List<String>> parameters = requestContext.request().parameters();

        String title = parameters.get("title").get(0);
        String year = parameters.get("year").get(0);
        LOG.info("Adding Book -> {} - {}", title, year);

        commandHandler.handle(new AddBook(title, year));

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
