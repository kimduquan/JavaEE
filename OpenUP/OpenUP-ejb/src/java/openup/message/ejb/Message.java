/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.message.ejb;

import openup.message.ejb.SessionBean;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueBrowser;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import openup.message.MessageConversation;
import openup.message.Session;

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
    private Session ss;
    
    @Inject
    private MessageConversation conversation;
    
    @POST
    public void startSession(){
        ss.getSession().start();
    }
    
    @GET
    @Path("/destination")
    public List<String> getDestinations() throws JMSException{
        List<String> result = new ArrayList<>();
        SessionBean ssBean = ss.getSession();
        QueueBrowser browser = ssBean.getBrowser();
        Enumeration msgs = browser.getEnumeration();
        while(msgs.hasMoreElements()){
            ObjectMessage msg = (ObjectMessage)msgs.nextElement();
            if(msg.propertyExists("callerPrincipalName")){
                String callerPrincipalName = msg.getStringProperty("callerPrincipalName");
                result.add(callerPrincipalName);
            }
        }
        return result;
    }
    
    @GET
    @Path("/conversation/{destination}/")
    public String getConversation(@PathParam("destination") String destination) throws JMSException{
        String result = "";
        Enumeration msgs = ss.getSession().getBrowser().getEnumeration();
        while(msgs.hasMoreElements()){
            ObjectMessage msg = (ObjectMessage)msgs.nextElement();
            if(msg.propertyExists("callerPrincipalName")){
                String callerPrincipalName = msg.getStringProperty("callerPrincipalName");
                if(callerPrincipalName.equals(destination)){
                    conversation.begin();
                    conversation.setPrincipalName(callerPrincipalName);
                    conversation.setDestination(msg.getJMSReplyTo());
                    result = conversation.getId();
                    break;
                }
            }
        }
        return result;
    }
    
    @POST
    @Path("/conversation")
    public void sendMessage(
            @FormParam("message") String message, @QueryParam("cid") String cid){
        if(!cid.isEmpty()){
            ss.getSession().getProducer().send(conversation.getDestination(), message);
            ss.getSession().getConsumer().receive();
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
