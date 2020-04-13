/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 *
 * @author FOXCONN
 */
@JMSDestinationDefinition(name = "java:app/TemporaryQueue", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "TemporaryQueue")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/TemporaryQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class TemporaryQueueBean extends TemporaryDestination {
    
    @EJB
    private QueueListenerBean queueBean;

    @Override
    protected void onTemporaryMessage(Message message) throws JMSException {
        queueBean.onMessage(message);
    }
    
    
}
