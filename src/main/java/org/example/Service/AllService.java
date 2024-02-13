package org.example.Service;

import com.mongodb.client.model.Filters;
import org.example.database.MongoDBConnectionHandlerImpl;

public class AllService {
    MongoDBConnectionHandlerImpl mongo = new MongoDBConnectionHandlerImpl();

    public AllService() {
    }

    public boolean checkEmail(String email) {
        boolean res = false;
        if (mongo.mongoDatabase.getCollection("Users").find(Filters.eq("email", email))
                .iterator().available() != 0) {
            res = true;
        }
        return res;
    }

}
