package command;

import com.spotify.apollo.RequestContext;

import java.util.List;
import java.util.Map;

public class Commands {

    public static AddBook addBookCommandFrom(RequestContext requestContext) {
        Map<String, List<String>> parameters = requestContext.request().parameters();

        String title = parameters.get("title").get(0);
        String year = parameters.get("year").get(0);

        return new AddBook(title, year);
    }
}
