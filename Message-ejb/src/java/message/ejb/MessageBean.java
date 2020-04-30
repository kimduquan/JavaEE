/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import message.context.MessageConversation;

/**
 *
 * @author FOXCONN
 */
@Stateless
@LocalBean
@Path("message")
public class MessageBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private ContextBean context;
    
    @Inject
    private MessageConversation conversation;
    
    @GET
    @Path("/destination")
    public List<String> getDestinations(){
        return context.getDestinations();
    }
    
    @POST
    @Path("/destination/{destination}/")
    public String beginConversation(@PathParam("destination") String destination) throws JMSException{
        String cid = "";
        Destination dest = context.getDestination(destination);
        if(dest != null){
            if(conversation.begin()){
                conversation.getConversation().begin(conversation.getId(), dest);
            }
        }
        return cid;
    }
    
    @POST
    @Path("/conversation")
    public void sendMessage(
            @QueryParam("cid") String cid,
            @FormParam("message") String message) throws JMSException{
        if(!cid.isEmpty()){
            context.send(conversation.getConversation().getConversation(), message);
        }
    }
    
    @DELETE
    @Path("/conversation")
    public void endConversation(@QueryParam("cid") String cid){
        if(!cid.isEmpty()){
            conversation.end();
        }
    }
}
