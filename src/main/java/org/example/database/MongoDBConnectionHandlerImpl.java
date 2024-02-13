package org.example.database;

import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDBConnectionHandlerImpl  {

    public MongoClient mongo;
    public MongoCredential mongoCredential;
    public MongoDatabase mongoDatabase;



    public MongoDBConnectionHandlerImpl() {
        try {
            mongo = MongoClients.create( "mongodb://localhost:27017" );
            mongoDatabase = mongo.getDatabase("Users");
        } catch (Exception e) {
            System.out.println("Error In Connection: " + e);
        }
    }

    /**
     * @param CollectionName = collection name
     */
    // Collection
    public void createCollection(String CollectionName) {
        try{
            mongoDatabase.createCollection(CollectionName);
            System.out.println("Collection <" + CollectionName + "> created successfully");
        } catch (Exception e) {
            System.out.println("Error In Create Collection: " + e);
        }
    }


    /**
     * @param CollectionName = collection name
     * @param oneDocument = eine Document
     */
    public void insertOneDocument(String CollectionName, Document oneDocument) {
        try {
            MongoCollection<Document> collection = mongoDatabase.getCollection(CollectionName);
            // Inserting document into the collection
            collection.insertOne(oneDocument);
            System.out.println("Document inserted successfully");
        } catch (Exception e) {
            System.out.println("Error In Insert One Document: " + e);
        }
    }

    public void deleteOneDocument(String CollectionName, Bson query){
        MongoCollection<Document> collection = mongoDatabase.getCollection(CollectionName);
        try {
            // Delete One document
            collection.deleteOne(query);
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    public void deleteManyDocuments(String CollectionName, Bson query){
        MongoCollection<Document> collection = mongoDatabase.getCollection(CollectionName);
        try {
            // Delete One document
            collection.deleteMany(query);
        } catch (MongoException me) {
            System.err.println("Unable to delete due to an error: " + me);
        }
    }

    public long countDocument(String CollectionName){
        MongoCollection<Document> collection = mongoDatabase.getCollection(CollectionName);
        return collection.countDocuments();
    }

}