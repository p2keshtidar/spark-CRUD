package org.example.rest;

import org.example.Controller.Controller;
import spark.Spark;

public class RESTHandler {
    private Controller controller;

    public RESTHandler(int port) {
        Spark.port(port);
        controller = new Controller();

        //  init
        controller.initRoutes();
    }

}
