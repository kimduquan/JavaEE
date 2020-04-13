/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import message.event.DestinationEvent;

/**
 *
 * @author FOXCONN
 */
@JMSDestinationDefinition(name = "java:app/Destination", interfaceName = "javax.jms.Queue", resourceAdapter = "jmsra", destinationName = "Destination")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/Destination"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class DestinationBean implements MessageListener {
    
    @EJB
    private ApplicationBean app;
    
    @Inject
    private JMSContext jms;
    
    @Inject
    @Push(channel="destination")
    private PushContext push;
    
    @Inject
    private Event<DestinationEvent> event;
    
    public DestinationBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage){
                Destination replyTo = message.getJMSReplyTo();
                if(message.propertyExists("destination") && replyTo != null){
                    onDestinationMessage((ObjectMessage)message);
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(MessagePushBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void onDestinationMessage(ObjectMessage message) throws JMSException{
        Destination replyTo = message.getJMSReplyTo();
        String name = message.getStringProperty("destination");
        HashMap<String, Destination> dests = app.getDestinations(name);
        Destination newDest = app.createDestination(name);
        JMSProducer producer = jms.createProducer();

        Message destMsg = createDestinationMessage(name, newDest);
        destMsg.setIntProperty("destinations", dests.size());
        producer.send(replyTo, destMsg);

        Collection<String> pushes = new ArrayList<>();
        dests.forEach((key, value) -> {
            try {
                sendDestination(producer, replyTo, key, value);
                pushes.add(key);
            } catch (JMSException ex) {
                Logger.getLogger(DestinationBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        push.send(name, pushes);
        DestinationEvent destEvent = new DestinationEvent();
        destEvent.setName(name);
        destEvent.setDestination(newDest);
        event.fire(destEvent);
    }
    
    Message createDestinationMessage(String name, Destination value) throws JMSException{
        ObjectMessage msg = jms.createObjectMessage();
        msg.setStringProperty("destination", name);
        msg.setJMSReplyTo(value);
        return msg;
    }
    
    void sendDestination(JMSProducer producer, Destination dest, String name, Destination value) throws JMSException{
        Message msg = createDestinationMessage(name, value);
        producer.send(dest, msg);
    }
}
