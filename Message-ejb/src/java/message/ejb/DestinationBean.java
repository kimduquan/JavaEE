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
@JMSDestinationDefinition(name = "java:app/Destination", interfaceName = "javax.jms.Topic", resourceAdapter = "jmsra", destinationName = "Destination")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "java:app/Destination"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/Destination"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "java:app/Destination"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class DestinationBean implements MessageListener {
    
    @EJB
    private ApplicationBean app;
    
    @Inject
    private JMSContext jms;
    
    public DestinationBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage){
                onObjectMessage((ObjectMessage)message);
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
                    msg.setStringProperty("principalName", key);
                    msg.setJMSReplyTo(value);
                    jms.createProducer().send(destination, msg);
                } catch (JMSException ex) {
                    Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            if(message.propertyExists("principalName")){
                String principalName = message.getStringProperty("principalName");
                app.getDestinations().put(principalName, destination);
            }
        }
    }
}
