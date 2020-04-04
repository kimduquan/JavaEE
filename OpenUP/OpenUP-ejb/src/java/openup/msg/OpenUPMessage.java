/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.msg;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.Message;
import javax.jms.MessageListener;

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
    
    public OpenUPMessage() {
    }
    
    @Override
    public void onMessage(Message message) {
    }
    
}
