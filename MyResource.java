package com.azuga.training;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
    public String tableName="csvFile";

    public static final Logger logger = LogManager.getLogger(MyResource.class.getName());//used to store the logs for this class

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("read")
    public String retrieveAll() {
        System.out.println(tableName);
        DBCRUDOperations opn = new DBCRUDOperations();
        String output = opn.read(tableName);
        if (output != null && !output.isBlank()) {
            return output;
        }
        return "data can not be retrieved from database";
    }

    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String delete(@PathParam("id") String id) {
        System.out.println(id);
        if (id != null && !id.isBlank()) {
            DBCRUDOperations opn = new DBCRUDOperations();
            String output = opn.delete(tableName, id);
            if (output != null)
                return output;
        }
        return "an error occurred while deleting data";
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String update(@PathParam("id") String cond, String input) {
        System.out.println(input);
        System.out.println(cond);
        if (input != null && !input.isBlank() && cond != null && !cond.isBlank()) {
            DBCRUDOperations opn = new DBCRUDOperations();
            String output = opn.update(tableName, input, cond);
            if (output != null)
                return output;
        }
        return "error in updating table";
    }

    @POST
    @Path("/insert")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String insert(String input) {
        if (input != null && !input.isBlank()) {
            DBCRUDOperations opn = new DBCRUDOperations();
            logger.debug(input);
            String output = opn.insert(tableName, new StringBuilder(input));
            if (output != null) {
                return output;
            }
        }
        return "error in inserting data";
    }

    @POST
    @Path("/file")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String create(String input) {
        if (input != null && !input.isBlank() && tableName != null && !tableName.isBlank()) {
            StringBuilder str = new StringBuilder(input);
            DBCRUDOperations opn = new DBCRUDOperations();
            logger.debug(input);
            String output = null;
            try {
            if (str.charAt(0) == '[') {
                str.replace(0, 1, "");
                str.replace(str.length() - 1, str.length(), "");
            }
            String[] arr = str.toString().split("},\\{");

            System.out.println(tableName);
            for (int i = 0; i < arr.length; i++) {
                if(i==0){
//                    System.out.println(arr[i] + "}");
                    output = opn.create(tableName, new StringBuilder(arr[i]+"}"));
                    opn.insert(tableName, new StringBuilder(arr[i]+"}"));
                }
                else if (i == arr.length - 1) {
                    opn.insert(tableName, new StringBuilder("{" + arr[i]));
//                    System.out.println("{" + arr[i]);
                } else {
                    opn.insert(tableName, new StringBuilder("{" + arr[i] + "}"));
//                    System.out.println("{" + arr[i] + "}");
                }
            }
            } catch (SQLException e) {
                output = e.getMessage();
            }
            if (output != null) {
                return output;
            }
            return "error in creating the table";
        }
        return "null values received";
    }
}
