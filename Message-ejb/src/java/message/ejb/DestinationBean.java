/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
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
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import message.DestinationDefinition;

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
    private Event<DestinationDefinition> event;
    
    @Inject
    @Push(channel="destination")
    private PushContext push;
    
    public DestinationBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage)message;
                Destination replyTo = objMsg.getJMSReplyTo();
                if(objMsg.getObject() instanceof DestinationDefinition && replyTo != null){
                    
                    DestinationDefinition destDef = (DestinationDefinition)objMsg.getObject();
                    HashMap<String, String> config = new HashMap<>();
                    Enumeration names = objMsg.getPropertyNames();
                    while(names.hasMoreElements()){
                        String name = (String)names.nextElement();
                        String value = objMsg.getStringProperty(name);
                        config.put(name, value);
                    }
                    
                    Destination destination = app.createDestination(config, destDef);
                    objMsg.setJMSReplyTo(destination);
                    jms.createProducer().send(replyTo, objMsg);
                    
                    event.fire(destDef);
                    Collection<String> destDefs = new ArrayList<>();
                    Enumeration destinations = jms.createBrowser(app.getDestinations()).getEnumeration();
                    while(destinations.hasMoreElements()){
                        ObjectMessage destMsg = (ObjectMessage)destinations.nextElement();
                        jms.createProducer().send(destMsg.getJMSReplyTo(), objMsg);
                        jms.createProducer().send(objMsg.getJMSReplyTo(), destMsg);
                        DestinationDefinition def = (DestinationDefinition)destMsg.getObject();
                        destDefs.add(def.name);
                    }
                    push.send(destDef.name, destDefs);
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(MessagePushBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
