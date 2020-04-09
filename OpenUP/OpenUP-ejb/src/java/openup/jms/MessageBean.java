/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

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
public class MessageBean implements MessageListener {
    
    @EJB 
    private ApplicationBean app;
    
    @Inject
    private JMSContext jms;
    
    @Inject
    @Push(channel="message")
    private PushContext push;
    
    public MessageBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage){
                onObjectMessage((ObjectMessage)message);
            }
            else if(message instanceof TextMessage){
                onTextMessage((TextMessage)message);
            }
            if(message != null){
                message.acknowledge();
            }
        } catch (JMSException ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void onObjectMessage(ObjectMessage message) throws JMSException{
        Destination destination = message.getJMSReplyTo();
        if(destination != null){
            app.getDestinations().forEach((key, value) -> {
                try {
                    ObjectMessage msg = jms.createObjectMessage();
                    msg.setStringProperty("callerPrincipalName", key);
                    msg.setJMSReplyTo(value);
                    jms.createProducer().send(destination, msg);
                } catch (JMSException ex) {
                    Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            if(message.propertyExists("callerPrincipalName")){
                String callerPrincipalName = message.getStringProperty("callerPrincipalName");
                app.getDestinations().put(callerPrincipalName, destination);
            }
        }
    }
    
    void onTextMessage(TextMessage message) throws JMSException {
        if(message.propertyExists("callerPrincipalName")){
            String callerPrincipalName = message.getStringProperty("callerPrincipalName");
            if(app.getDestinations().containsKey(callerPrincipalName)){
                push.send(message.getText(), callerPrincipalName);
            }
        }
    }
}
