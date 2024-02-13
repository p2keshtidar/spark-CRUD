package org.example.rest;

import org.example.Controller.Controller;
import org.example.database.MongoDBConnectionHandlerImpl;

public class RESTHandler {
    private Controller controller;

    public RESTHandler(int port, MongoDBConnectionHandlerImpl mongoDBConnectionHandlerImpl) throws Exception {
        RESTConfig.configure(port);
        controller = new Controller();

        //  init
        controller.initRoutes();
    }

}
