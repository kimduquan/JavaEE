/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author FOXCONN
 */
@JMSDestinationDefinition(name = "java:app/Message", interfaceName = "javax.jms.Topic", resourceAdapter = "jmsra", destinationName = "Message")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "java:app/Message"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/Message"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "java:app/Message"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class MessagePushBean implements MessageListener {
    
    @EJB
    private ApplicationBean app;
    
    @Inject
    @Push(channel="message")
    private PushContext push;
    
    public MessagePushBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage){
                onTextMessage((TextMessage)message);
            }
            if(message != null){
                message.acknowledge();
            }
        } catch (JMSException ex) {
            Logger.getLogger(MessagePushBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void onTextMessage(TextMessage message) throws JMSException {
        if(message.propertyExists("destination")){
            String destination = message.getStringProperty("destination");
            if(app.getDestinations().containsKey(destination)){
                push.send(message.getText(), destination);
            }
        }
    }
}
