/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.event;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author FOXCONN
 */
@Path("event")
public interface Events {
    
    @GET
    @Path("{name}/{event: .+}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    void register(
            @PathParam("name") 
            String name, 
            @PathParam("event")
            List<PathSegment> event
    );
}
