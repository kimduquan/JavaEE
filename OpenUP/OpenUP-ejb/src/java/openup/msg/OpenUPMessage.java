/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.msg;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TemporaryQueue;

/**
 *
 * @author FOXCONN
 */
@JMSDestinationDefinition(name = "java:app/OpenUP", interfaceName = "javax.jms.Topic", resourceAdapter = "jmsra", destinationName = "OpenUP")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "java:app/OpenUP"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/OpenUP"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "java:app/OpenUP"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class OpenUPMessage implements MessageListener {
    
    ConcurrentHashMap<String, TemporaryQueue> queues = new ConcurrentHashMap<String, TemporaryQueue>();
    
    @Inject
    JMSContext jms;
    
    @Inject @Push(channel = "openup")
    PushContext push;
    
    public OpenUPMessage() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)message;
                if(objMsg.propertyExists("name")){
                    String name = objMsg.getStringProperty("name");
                    if(name.equals("sessionCreated")){
                        String sessionId = objMsg.getStringProperty("sessionId");
                        TemporaryQueue queue = jms.createTemporaryQueue();
                        queues.put(sessionId, queue);
                    }
                    else if(name.equals("sessionDestroyed")){
                        String sessionId = objMsg.getStringProperty("sessionId");
                        if(queues.containsKey(sessionId)){
                            TemporaryQueue queue=queues.get(sessionId);
                            queues.remove(sessionId);
                            queue.delete();
                        }
                    }
                }
            }
            else {
                if(message.propertyExists("destination")){
                    String destination = message.getStringProperty("destination");
                    if(queues.containsKey(destination)){
                        TemporaryQueue destQueue = queues.get(destination);
                        if(message.propertyExists("replyTo")){
                            String replyTo = message.getStringProperty("replyTo");
                            if(queues.containsKey(replyTo)){
                                TemporaryQueue replyToQueue = queues.get(replyTo);
                                message.setJMSReplyTo(replyToQueue);
                            }
                        }
                        jms.createProducer().send(destQueue, message);
                    }
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(OpenUPMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
