package command;

import com.spotify.apollo.RequestContext;

import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class Commands {

    public static RateBook rateBookCommandFrom(RequestContext requestContext) {
        Map<String, List<String>> parameters = requestContext.request().parameters();

        String title = parameters.get("title").get(0);
        String stars = parameters.get("stars").get(0);
        String comment = parameters.get("comment").get(0);

        return new RateBook(title, comment, parseInt(stars));
    }

    public static AddBook addBookCommandFrom(RequestContext requestContext) {
        Map<String, List<String>> parameters = requestContext.request().parameters();

        String title = parameters.get("title").get(0);
        String year = parameters.get("year").get(0);

        return new AddBook(title, year);
    }
}
