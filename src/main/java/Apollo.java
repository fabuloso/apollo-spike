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
        repository = new MongoDbRepository();
        seed(repository);
        HttpService.boot(Apollo::init, "apollo", args);
    }

    static void init(Environment environment) {
        environment.routingEngine()
                .registerAutoRoute(Route.sync("GET", "/first-view", Apollo::firstView))
                .registerAutoRoute(Route.sync("GET", "/ping", context -> "pong"));
    }

    private static Response<ByteString> firstView(RequestContext requestContext) {
        String result = new GetFirstViewCommand(repository).execute();
        return Response.ok().withPayload(ByteString.encodeUtf8(result));
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
