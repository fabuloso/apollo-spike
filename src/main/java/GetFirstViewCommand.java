import com.mongodb.client.FindIterable;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetFirstViewCommand extends HystrixCommand<String> {

    private MongoDbRepository repository;
    final static Logger LOG = LoggerFactory.getLogger(GetFirstViewCommand.class);

    public GetFirstViewCommand(MongoDbRepository repository) {
        super(HystrixCommandGroupKey.Factory.asKey("GetViews"));
        this.repository = repository;
    }

    @Override
    protected String run() {
        FindIterable<Document> documents = repository.findAll();
        return documents.first().toJson();
    }

    @Override
    protected String getFallback() {
        LOG.warn("FALLBACK CALLED");
        return "{}";
    }
}