/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.QueueBrowser;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;
import message.event.DestinationEvent;
import message.context.Session;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class SessionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Inject
    private JMSContext jms;
    
    private ConcurrentHashMap<String, Destination> destinationEvents = new ConcurrentHashMap<>();
    
    @Resource(mappedName="java:app/Destination")
    private Topic destinations;
    
    @Inject
    private Principal principal;
    
    private Destination destination;
    
    public void start() throws JMSException{
        ObjectMessage msg = jms.createObjectMessage();
        TemporaryQueue tempQueue = jms.createTemporaryQueue();
        msg.setStringProperty("destination", principal.getName());
        msg.setJMSReplyTo(tempQueue);
        jms.createProducer().send(destinations, msg);
        Message reply = jms.createConsumer(tempQueue).receive();
        this.destination = reply.getJMSReplyTo();
    }
    
    @Remove
    public void remove(){
        
    }
    
    public void onNewDestination(@Observes(notifyObserver=Reception.IF_EXISTS) DestinationEvent event){
        destinationEvents.put(event.getName(), event.getDestination());
    }
    
    public List<String> getDestinations(){
        ArrayList<String> dests = new ArrayList<>();
        destinationEvents.forEach((key, value) -> {
            dests.add(key);
        });
        return dests;
    }
    
    public Destination getDestination(String name){
        return destinationEvents.getOrDefault(name, null);
    }
}
