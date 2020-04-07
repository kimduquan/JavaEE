/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author FOXCONN
 */
@Stateless
@LocalBean
@Path("message")
public class Message {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Inject
    MessageBean msg;
    
    @GET
    public List<String> getDestinations(){
        return msg.getDestinations();
    }
    
    @POST
    @Path("/{destination}/")
    public void send(
            @PathParam("destination") String destination, 
            @FormParam("message") String message){
        msg.send(destination, message);
    }
}
