package org.example;

import org.example.database.MongoDBConnectionHandlerImpl;
import org.example.rest.RESTHandler;

import static spark.Spark.staticFiles;

public class Main {

    public static void main(String[] args) throws Exception {
        staticFiles.location("/public");


        MongoDBConnectionHandlerImpl mongoDBConnection = new MongoDBConnectionHandlerImpl();

        RESTHandler restHandler = new RESTHandler(8080, mongoDBConnection);

    }

}
