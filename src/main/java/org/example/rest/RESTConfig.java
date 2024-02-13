package org.example.rest;

import static spark.Spark.port;

public class RESTConfig {
    public static void configure(int port){
        port(port);
    }
}