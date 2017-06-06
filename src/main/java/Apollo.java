import com.spotify.apollo.Environment;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import command.AddBook;
import command.CommandHandler;
import command.LendBackBook;
import command.LendBook;
import okio.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spotify.apollo.Response.ok;
import static com.spotify.apollo.route.Route.sync;
import static command.Commands.addBookCommandFrom;

public class Apollo {

    private static CommandHandler commandHandler;

    final static Logger LOG = LoggerFactory.getLogger(Apollo.class);


    public static void main(String[] args) throws LoadingException {
        LOG.info("INIT COMMAND HANDLERS");
        commandHandler = new CommandHandler();

        LOG.info("INIT ROUTES");
        HttpService.boot(Apollo::init, "apollo", args);
    }

    static void init(Environment environment) {
        environment.routingEngine()
                .registerAutoRoute(sync("GET", "/addBook",Apollo::addBook))
                .registerAutoRoute(sync("GET", "/lendBook", Apollo::lendBook))
                .registerAutoRoute(sync("GET", "/lendBackBook", Apollo::lendBackBook));
    }

    private static Response<ByteString> lendBook(RequestContext requestContext) {
        String title = requestContext.request().parameter("title").get();
        String name = requestContext.request().parameter("name").get();

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
}
