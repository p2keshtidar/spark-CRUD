package org.example.Controller;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.example.Domain.User;
import org.example.Service.AllService;
import org.example.database.MongoDBConnectionHandlerImpl;
import spark.Spark;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;

public class Controller {
    private AllService service = new AllService();
    private final MongoDBConnectionHandlerImpl mongo = new MongoDBConnectionHandlerImpl();

    public void initRoutes() {

        //-------------------------------------------- GET --------------------------------------------------------------

        // with service method
        get("/checkEmail", (request, response) -> new Document()
                .append("result", service.checkEmail(request.queryParams("email"))).toJson());



        // without service method
        get("/email", (request, response) -> {
            boolean res = false;
            if (mongo.mongoDatabase.getCollection("Users")
                    .find(Filters.eq("email", request.queryParams("email")))
                    .iterator().available() != 0) {
                res = true;
            }
            return new Document().append("result", res).toJson();
        });

        //------------------------------------------------ CREAT -------------------------------------------------------

        // create first Information
        post("/signup", (request, response) -> {
            User user = new User(request.queryParams("email"),
                    request.queryParamOrDefault("password",
                            request.queryParams("email") + "pass")
                    , request.queryParamOrDefault("firstname",
                    System.getProperties().get("user.name").toString())
                    , request.queryParams("lastname"));
            mongo.mongoDatabase.getCollection("Users").insertOne(new Document()
                    .append("email", user.getEmail())
                    .append("password", user.getPassword())
                    .append("firstname", user.getFirstName())
                    .append("lastname", user.getLastName()));
            return new Document("result", "success").toJson();
        });

        //------------------------------------------------ UPDATE ------------------------------------------------------

        post("/updateEmail", (request, response) -> {

            mongo.mongoDatabase.getCollection("Users")
                    .findOneAndUpdate(new Document().append("email", request.queryParams("oldEmail")),
                            Updates.set("email", request.queryParams("newEmail")));

            return new Document("result", "success").toJson();
        });



        //----------------------------------------------- DELETE -------------------------------------------------------
        delete("/delete", (request, response) -> mongo.mongoDatabase.getCollection("Users")
                .deleteOne(new Document().append("email", request.queryParams("email"))));

    }
}