import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDbRepository {

    public static final String VIEWS = "views";
    public static final String DATABASE_NAME = "spike";

    MongoDatabase database;

    public MongoDbRepository() {
        MongoClient client = new MongoClient("mongo" , 27017);
        database = client.getDatabase(DATABASE_NAME);
    }

    public void drop() {
        database.getCollection(VIEWS).drop();
    }

    public void insert(String json) {
        database.getCollection(VIEWS).insertOne(Document.parse(json));
    }

    public FindIterable<Document> findAll() {
        FindIterable<Document> views = database.getCollection(VIEWS).find();
        return views;
    }

}