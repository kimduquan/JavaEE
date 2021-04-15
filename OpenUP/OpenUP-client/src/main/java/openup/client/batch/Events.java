/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("batch/event")
public interface Events {
    
    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    void register(@QueryParam("executionId") long executionId);
}
