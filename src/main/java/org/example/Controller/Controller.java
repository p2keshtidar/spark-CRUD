package org.example.Controller;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.Domain.Project;
import org.example.Domain.User;
import org.example.Service.AllService;
import org.example.database.MongoDBConnectionHandlerImpl;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static spark.Spark.get;
import static spark.Spark.post;

public class Controller {
    private AllService service = new AllService();
    private final MongoDBConnectionHandlerImpl mongo = new MongoDBConnectionHandlerImpl();

    public Controller() throws IOException {
    }


    public void initRoutes() {

        get("/hello", (request, response) -> {
            return "hello";
        });

        get("/checkEmail", (request, response) -> {
            return service.checkEmail(request.queryParams("email"));
        });

        post("/signup", (request, response) -> {
            User user = new User(request.queryParams("email"),
                    request.queryParamOrDefault("password",
                            new Date(System.currentTimeMillis()).toString())
                    , request.queryParamOrDefault("firstname", System.getProperties().get("user.name").toString())
                    , request.queryParams("lastname"));
            mongo.mongoDatabase.getCollection("Users").insertOne(new Document().append("email", user.getEmail()
                    ).append("password", user.getPassword()).append("firstname", user.getFirstName())
                    .append("lastname", user.getLastName()).append("projectList", user.getProjectList()));
            return "";
        });

        post("/addProj", (request, response) -> {
            int id = 0;
            List<Document> projects = new ArrayList<>();
            mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first()
                    .get("projectList", ArrayList.class).iterator().forEachRemaining(o -> projects.add((Document) o));
            Document oldDoc = mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first();
            projects.add(new Document().append("id", 1)
                    .append("category", request.queryParams("category"))
                    .append("description", request.queryParams("description")));
            String email = oldDoc.getString("email");
            String password = oldDoc.getString("password");
            String firstname = oldDoc.getString("firstname");
            String lastname = oldDoc.getString("lastname");
            mongo.mongoDatabase.getCollection("Users")
                    .deleteMany(Filters.eq("email", request.queryParams("email")));
            mongo.mongoDatabase.getCollection("Users")
                    .insertOne(new Document().append("projectList", projects)
                            .append("email", email)
                            .append("password", password)
                            .append("firstname", firstname)
                            .append("lastname", lastname)
                            .append("projectList", projects));
            return "";
        });


    }
}