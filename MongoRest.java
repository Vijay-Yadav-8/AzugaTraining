package com.azuga.training;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource1")
public class MongoRest {
    public String tableName="museum1";

    final Logger logger = LogManager.getLogger(MyResource.class.getName());//used to store the logs for this class

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

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("read")
    public Response retrieveAll() {
        MongoCRUD opn = new MongoCRUD();
        String output = opn.read(tableName);
        if (output != null && !output.isBlank()) {
            return Response.status(200).entity(output).build();
        }
        return Response.status(500).entity("{\"message\":\"error occurred while reading the data from database\"}").build();
    }
    /**
     * Method handling HTTP DELETE requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response delete(@PathParam("id") String condition) {
        System.out.println(condition);
        if (condition != null && !condition.isBlank()) {
            MongoCRUD opn = new MongoCRUD();
            String output = opn.delete(tableName, condition);
            if (output != null)
                return Response.status(200).entity("{\"message\":\""+output+"\"}").build();
        }
        return Response.status(500).entity("{\"message\":\"an error occurred while deleting data\"}").build();
    }
    /**
     * Method handling HTTP PUT requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response update(@PathParam("id") String cond, String input) {
        System.out.println(input);
        System.out.println(cond);
        if (input != null && !input.isBlank() && cond != null && !cond.isBlank()) {
            MongoCRUD opn = new MongoCRUD();
            String output = opn.update(tableName, input, cond);
            if (output != null)
                return Response.status(200).entity("{\"message\":\""+output+"\"}").build();
        }
        return Response.status(500).entity("{\"message\":\"error in updating table\"}").build();
    }
    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("/insert")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response insert(String input) {
        if (input != null && !input.isBlank()) {
            MongoCRUD opn = new MongoCRUD();
            logger.debug(input);
            String output = opn.insert(tableName, new StringBuilder(input));
            if (output != null) {
                return Response.status(200).entity("{\"message\":\""+output+"\"}").build();
            }
        }
        return Response.status(500).entity("{\"message\":\"error in inserting data\"}").build();
    }
    /**
     * Method handling HTTP POST requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("/file")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(String input) {
        if (input != null && !input.isBlank() && tableName != null && !tableName.isBlank()) {
            StringBuilder str = new StringBuilder(input);
            MongoCRUD opn = new MongoCRUD();
            logger.debug(input);
            String output = null;
                if (str.charAt(0) == '[') {
                    str.replace(0, 1, "");
                    str.replace(str.length() - 1, str.length(), "");
                }
                String[] arr = str.toString().split("},\\{");

                for (int i = 0; i < arr.length; i++) {
                    if (i == 0) {
                        output = opn.create(tableName, new StringBuilder(arr[i] + "}"));
                    } else if (i == arr.length - 1) {
                        opn.insert(tableName, new StringBuilder("{" + arr[i]));
                    } else {
                        opn.insert(tableName, new StringBuilder("{" + arr[i] + "}"));
                    }
                }
            if (output != null) {
                return Response.status(200).entity("{\"message\":\""+output+"\"}").build();
            }
            return Response.status(500).entity("{\"message\":\"error in creating the table\"}").build();
        }
        return Response.status(500).entity("{\"message\":\"null values received\"}").build();
    }
}
