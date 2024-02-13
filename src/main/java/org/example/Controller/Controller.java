package org.example.Controller;

import com.mongodb.client.model.Filters;
import org.bson.Document;
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
        return null;
        });

        post("/addProj", (request, response) -> {
            int id = 0;
            var list = mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first()
                    .get("projectList", ArrayList.class);
            var oldDoc = mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email"))).first();
            //ERROR
            list.add(new Project(id + 1,
                    request.queryParams("category"), request.queryParams("description")));
            var newDoc = new Document().append("email", oldDoc.getString("email"))
                    .append("lastname", oldDoc.getString("lastname"))
                    .append("firstname", oldDoc.getString("firstname"))
                    .append("password", oldDoc.getString("password"));
            mongo.mongoDatabase.getCollection("Users")
                    .deleteOne(oldDoc);
            mongo.mongoDatabase.getCollection("Users")
                    .insertOne(newDoc);
            return null;
        });


    }
}