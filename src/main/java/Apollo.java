import com.spotify.apollo.Environment;
import com.spotify.apollo.RequestContext;
import com.spotify.apollo.Response;
import com.spotify.apollo.httpservice.HttpService;
import com.spotify.apollo.httpservice.LoadingException;
import com.spotify.apollo.route.Route;
import okio.ByteString;

public class Apollo {

    private static MongoDbRepository repository;

    public static void main(String[] args) throws LoadingException {
        HttpService.boot(Apollo::init, "apollo", args);
        //repository = new MongoDbRepository();
        //seed(repository);
    }

    static void init(Environment environment) {
        environment.routingEngine()
                .registerAutoRoute(Route.sync("GET", "/", Apollo::doSomething))
                .registerAutoRoute(Route.sync("GET", "/hello", context -> "bye"));
    }

    private static Response<ByteString> doSomething(RequestContext requestContext) {
        //repository.findAll();
        return Response.ok().withPayload(ByteString.encodeUtf8("pong!"));
    }

    private static void seed(MongoDbRepository repository) {
        repository.drop();
        for (int i = 0; i < 10; i++) {
            String json = jsonFor(i);
            repository.insert(json);
        }
    }

    private static String jsonFor(int i) {
        return "{\"foo\":\"1\",\"bar\":{\"baz\":\"" + i + "\"}}";
    }
}
