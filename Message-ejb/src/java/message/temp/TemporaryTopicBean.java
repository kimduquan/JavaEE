/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * @author FOXCONN
 */
@JMSDestinationDefinition(name = "java:app/TemporaryTopic", interfaceName = "javax.jms.Topic", resourceAdapter = "jmsra", destinationName = "TemporaryTopic")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "java:app/TemporaryTopic"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/TemporaryTopic"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "java:app/TemporaryTopic"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class TemporaryTopicBean extends TemporaryDestination {

    @Override
    protected void onTemporaryMessage(Message message) throws JMSException {
        
    }
    
}
