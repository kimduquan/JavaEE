/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

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
    
    ConcurrentHashMap<String, Destination> destinations = new ConcurrentHashMap<>();
    
    @Inject
    JMSContext jms;
    
    public OpenUPMessage() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage){
                onObjectMessage((ObjectMessage)message);
            }
            message.acknowledge();
        } catch (JMSException ex) {
            Logger.getLogger(OpenUPMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void onObjectMessage(ObjectMessage message) throws JMSException{
        Destination destination = message.getJMSReplyTo();
        if(destination != null){
            destinations.forEach((key, value) -> {
                try {
                    ObjectMessage msg = jms.createObjectMessage();
                    msg.setStringProperty("callerPrincipalName", key);
                    msg.setJMSReplyTo(value);
                    jms.createProducer().send(destination, msg);
                } catch (JMSException ex) {
                    Logger.getLogger(OpenUPMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            if(message.propertyExists("callerPrincipalName")){
                String callerPrincipalName = message.getStringProperty("callerPrincipalName");
                destinations.put(callerPrincipalName, destination);
            }
        }
    }
}
