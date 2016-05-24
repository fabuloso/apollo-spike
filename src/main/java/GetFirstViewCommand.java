import com.mongodb.client.FindIterable;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.bson.Document;

public class GetFirstViewCommand extends HystrixCommand<String> {

    private MongoDbRepository repository;

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
        return "{}";
    }
}