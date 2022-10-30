package com.azuga.training;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.Set;

/**
 * This class is used to do crud operations on mongodb.
 */
public class MongoCRUD {
    public static final Logger logger = LogManager.getLogger(MongoCRUD.class.getName());//used to store the logs for this class

    /**
     * This method is used to read the data from the database in the form of string
     * @param collectionName-It is used to select specific collection
     * @return -returns whole collection data as a string
     */
    public String read(String collectionName) {
        StringBuilder output = new StringBuilder("[");
        // Creating a Mongo client
        MongoClient mongo = null;
        try {
            mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db = mongo.getDatabase("museumdb");
            MongoCollection<Document> collection = db.getCollection(collectionName);
            FindIterable<Document> cursor = collection.find();
            for (Document document : cursor) {
                Set<String> set = document.keySet();
                output.append("{");
                set.forEach(s -> {
                    output.append("\"").append(s).append("\"");
                    output.append(":");
                    output.append("\"").append(document.get(s)).append("\"");
                    output.append(",");
                });
                output.deleteCharAt(output.length() - 1);
                output.append("},");
            }
            output.deleteCharAt(output.length() - 1);
            return output.append("]").toString();
        } catch (Exception e) {
            logger.error("{} occurred while dealing with mongodb", e.getMessage());
        } finally {
            if (mongo != null)
                mongo.close();
            else
                logger.error("An error occurred while closing the connection");
        }
        return null;
    }
    /**
     * This method is used to delete the data from the database
     * @param collectionName-It is used to select specific collection
     * @param condition -used to specify the condition
     * @return -returns status data as a string
     */
    public String delete(String collectionName, String condition) {
        logger.debug("{} is the given collection input", collectionName);
        logger.debug("{} is the given condition to delete the record", condition);
        String output = null;
        MongoClient mongo = null;
        try {
            mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db = mongo.getDatabase("museumdb");
            MongoCollection<Document> collection = db.getCollection(collectionName);
            String[] s = condition.split("=");
            System.out.println("divided1 " + s[0] + " = " + s[1]);
            DeleteResult result = collection.deleteOne(Filters.eq(s[0],s[1]));
            System.out.println(result);
            output = result.getDeletedCount() + " record/s deleted successfully";
            logger.info("Acknowledgement from mongodb is {}", output);
        } catch (Exception e) {
            logger.error("{} occurred while dealing with mongodb", e.getMessage());
        } finally {
            if (mongo != null)
                mongo.close();
            else
                logger.error("An error occurred while closing the connection");
        }
        return output;
    }
    /**
     * This method is used to delete the data from the database
     * @param collectionName-It is used to select specific collection
     * @param json -string which will be created in the database
     * @return -returns status data as a string
     */
    public String insert(String collectionName, StringBuilder json) {
        String output = null;
        MongoClient mongo = null;
        try {
            mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db = mongo.getDatabase("museumdb");
            MongoCollection<Document> collection = db.getCollection(collectionName);
            collection.insertOne(Document.parse(json.toString()));
            output = "1 record inserted successfully";
        } catch (Exception e) {
            logger.error("{} occurred while dealing with mongodb", e.getMessage());
        } finally {
            if (mongo != null)
                mongo.close();
            else
                logger.error("An error occurred while closing the connection");
        }
        return output;
    }
    /**
     * This method is used to update the data in the database
     * @param collectionName-It is used to select specific collection
     * @param condition -used to specify the condition
     * @param json -the string which will be updated to.
     * @return -returns status data as a string
     */
    public String update(String collectionName,String json,String condition){
        String output = null;
        MongoClient mongo = null;
        try {
            mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db = mongo.getDatabase("museumdb");
            MongoCollection<Document> collection = db.getCollection(collectionName);
            logger.debug("json data received from frontend{}",Document.parse(json));
            String[] s = condition.split("=");
            System.out.println("Update 1 "+s[0]+"="+s[1]);
            collection.replaceOne(Filters.eq(s[0],s[1]),Document.parse(json));
            output = "1 record updated successfully";
        } catch (MongoException e) {
            logger.error("{} occurred while dealing with mongodb", e.getMessage());
        } finally {
            if (mongo != null)
                mongo.close();
            else
                logger.error("An error occurred while closing the connection");
        }

        return output;
    }
    /**
     * This method is used to delete the data from the database
     * @param collectionName-It is used to select specific collection
     * @param json -string which will be created in the database
     * @return -returns status data as a string
     */
    public String create(String collectionName, StringBuilder json) {
        String output=null;
        MongoClient mongo = null;
        if(collectionName!=null && json!=null) {
            try {
                mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
                MongoDatabase db = mongo.getDatabase("museumdb");
                MongoCollection<Document> collection = db.getCollection(collectionName);
                collection.insertOne(Document.parse(json.toString()));
                logger.debug("json data received from frontend{}", Document.parse(json.toString()));
                output = "Table created successfully";
            } catch (Exception e) {
                logger.error("{} occurred while dealing with mongodb", e.getMessage());
            } finally {
                if (mongo != null)
                    mongo.close();
                else
                    logger.error("An error occurred while closing the connection");
            }
        }else
            logger.error("mongodb can not work with null values because either collectionName i.e {} " +
                    "or json i.e {}",collectionName,json);
        return output;
    }
}
