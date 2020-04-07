/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
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
    Session ss;
    
    @Inject
    JMSContext jms;
    
    @GET
    public List<String> getDestinations() throws JMSException{
        List<String> result = new ArrayList<>();
        Enumeration msgs = ss.browser.getEnumeration();
        while(msgs.hasMoreElements()){
            ObjectMessage msg = (ObjectMessage)msgs.nextElement();
            if(msg.propertyExists("callerPrincipalName")){
                String callerPrincipalName = msg.getStringProperty("callerPrincipalName");
                result.add(callerPrincipalName);
            }
        }
        return result;
    }
    
    @POST
    @Path("/{destination}/")
    public void send(
            @PathParam("destination") String destination, 
            @FormParam("message") String message) throws JMSException{
        Enumeration msgs = ss.browser.getEnumeration();
        while(msgs.hasMoreElements()){
            ObjectMessage msg = (ObjectMessage)msgs.nextElement();
            if(msg.propertyExists("callerPrincipalName")){
                String callerPrincipalName = msg.getStringProperty("callerPrincipalName");
                if(callerPrincipalName.equals(destination)){
                    TextMessage newMsg = jms.createTextMessage(message);
                    newMsg.setStringProperty("callerPrincipalName", destination);
                    ss.producer.send(msg.getJMSReplyTo(), newMsg);
                    ss.consumer.receive();
                    break;
                }
            }
        }
    }
}
