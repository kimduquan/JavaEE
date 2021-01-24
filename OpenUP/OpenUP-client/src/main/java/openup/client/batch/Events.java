/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("batch/events")
public interface Events {
    
    @GET
    @Path("{executionId}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    void register(@PathParam("executionId") long executionId);
}
