package org.example.Controller;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.example.Domain.User;
import org.example.Service.AllService;
import org.example.database.MongoDBConnectionHandlerImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static spark.Spark.get;
import static spark.Spark.post;

public class Controller {
    private AllService service = new AllService();
    private final MongoDBConnectionHandlerImpl mongo = new MongoDBConnectionHandlerImpl();

    public void initRoutes() {

        get("/checkEmail", (request, response) -> {

            return  new Document().append("result", service.checkEmail(request.queryParams("email"))).toJson();
        });

        post("/signup", (request, response) -> {
            User user = new User(request.queryParams("email"),
                    request.queryParamOrDefault("password",
                            new Date(System.currentTimeMillis()).toString())
                    , request.queryParamOrDefault("firstname", System.getProperties().get("user.name").toString())
                    , request.queryParams("lastname"));
            mongo.mongoDatabase.getCollection("Users").insertOne(new Document()
                    .append("email", user.getEmail())
                    .append("password", user.getPassword())
                    .append("firstname", user.getFirstName())
                    .append("lastname", user.getLastName())
                    .append("projectList", user.getProjectList())
                    .append("bids", new ArrayList<>()));
            return new Document("result", "success").toJson();
        });

        post("/addProj", (request, response) -> {
            List<Document> projects = new ArrayList<>();
            List<Document> bids = new ArrayList<>();
            Document oldDoc = mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first();
            String email = oldDoc.getString("email");
            String password = oldDoc.getString("password");
            String firstname = oldDoc.getString("firstname");
            String lastname = oldDoc.getString("lastname");
            mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first()
                    .get("projectList", ArrayList.class).iterator().forEachRemaining(o -> projects.add((Document) o));
            mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first()
                    .get("bids", ArrayList.class).iterator().forEachRemaining(o -> bids.add((Document) o));
            projects.add(new Document().append("id", 1)
                    .append("category", request.queryParams("category"))
                    .append("description", request.queryParams("description")));
            mongo.mongoDatabase.getCollection("Users")
                    .deleteMany(Filters.eq("email", request.queryParams("email")));
            mongo.mongoDatabase.getCollection("Users")
                    .insertOne(new Document().append("projectList", projects)
                            .append("bids", bids)
                            .append("email", email)
                            .append("password", password)
                            .append("firstname", firstname)
                            .append("lastname", lastname)
                            .append("projectList", projects));
            return new Document("result", "success").toJson();
        });

        post("/addBid", (request, response) -> {
            List<Document> projects = new ArrayList<>();
            List<Document> bids = new ArrayList<>();
            Document oldDoc = mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first();
            String email = oldDoc.getString("email");
            String password = oldDoc.getString("password");
            String firstname = oldDoc.getString("firstname");
            String lastname = oldDoc.getString("lastname");
            mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first()
                    .get("projectList", ArrayList.class).iterator().forEachRemaining(o -> projects.add((Document) o));
            mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first()
                    .get("bids", ArrayList.class).iterator().forEachRemaining(o -> bids.add((Document) o));
            bids.add(new Document().append("projId", 1)
                            .append("bider", request.queryParams("Pemail"))
                    .append("price", request.queryParams("price"))
                    .append("description", request.queryParams("description"))
                    .append("months", request.queryParams("months"))
                    .append("weeks", request.queryParams("weeks"))
                    .append("days", request.queryParams("days"))
                    .append("weeks", request.queryParams("weeks")));
            mongo.mongoDatabase.getCollection("Users")
                    .deleteMany(Filters.eq("email", request.queryParams("email")));
            mongo.mongoDatabase.getCollection("Users")
                    .insertOne(new Document().append("projectList", projects)
                            .append("email", email)
                            .append("password", password)
                            .append("firstname", firstname)
                            .append("lastname", lastname)
                            .append("projectList", projects)
                            .append("bids", bids));
            return new Document("result", "success").toJson();
        });


    }
}