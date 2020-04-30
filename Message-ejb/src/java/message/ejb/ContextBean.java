/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;
import message.DestinationDefinition;
import message.context.Session;

/**
 *
 * @author FOXCONN
 */
@Stateless
@LocalBean
public class ContextBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Inject
    private JMSContext jms;
    
    @Inject
    private Session ss;
    
    @Resource(mappedName="java:app/Destination")
    private Queue destinations;
    
    public Queue createQueue(String name) throws JMSException{
        DestinationDefinition destDef = new DestinationDefinition();
        destDef.name = name;
        destDef.interfaceName = "javax.jms.Queue";
        destDef.resourceAdapter = "jmsra";
        destDef.destinationName = name;
        
        ObjectMessage createMsg = jms.createObjectMessage(destDef);
        TemporaryQueue receiver = jms.createTemporaryQueue();
        JMSConsumer consumer = jms.createConsumer(receiver);
        createMsg.setJMSReplyTo(receiver);
        
        createMsg.setStringProperty("destinationLookup", destDef.name);
        createMsg.setStringProperty("destinationType", destDef.interfaceName);
        
        jms.createProducer().send(destinations, createMsg);
        
        Message replyMsg = consumer.receive();
        Queue queue = (Queue) replyMsg.getJMSReplyTo();
        return queue;
    }
    
    public Topic createTopic(String name) throws JMSException{
        DestinationDefinition destDef = new DestinationDefinition();
        destDef.name = name;
        destDef.interfaceName = "javax.jms.Topic";
        destDef.resourceAdapter = "jmsra";
        destDef.destinationName = name;
        
        ObjectMessage createMsg = jms.createObjectMessage(destDef);
        TemporaryQueue receiver = jms.createTemporaryQueue();
        JMSConsumer consumer = jms.createConsumer(receiver);
        createMsg.setJMSReplyTo(receiver);
        
        createMsg.setStringProperty("clientId", destDef.name);
        createMsg.setStringProperty("destinationLookup", destDef.name);
        createMsg.setStringProperty("subscriptionDurability", "Durable");
        createMsg.setStringProperty("subscriptionName", destDef.name);
        createMsg.setStringProperty("destinationType", "javax.jms.Topic");
        
        jms.createProducer().send(destinations, createMsg);
        
        Message replyMsg = consumer.receive();
        Topic topic = (Topic) replyMsg.getJMSReplyTo();
        return topic;
    }
    
    public  Destination getDestination(String name){
        return getDestination(ss.getSession().getDestinations(), name);
    }
    
    protected  Destination getDestination(Queue from, String name){
        Destination dest = null;
        try {
            Enumeration enumeration = jms.createBrowser(from).getEnumeration();
            while(enumeration.hasMoreElements()){
                ObjectMessage destMsg = (ObjectMessage) enumeration.nextElement();
                DestinationDefinition destDef = (DestinationDefinition)destMsg.getObject();
                if(destDef.name.equals(name)){
                    dest = destMsg.getJMSReplyTo();
                    break;
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dest;
    }
    
    protected List<String> getDestinations(Queue from){
        ArrayList<String> dests = new ArrayList<>();
        try {
            Enumeration enumeration = jms.createBrowser(from).getEnumeration();
            while(enumeration.hasMoreElements()){
                ObjectMessage destMsg = (ObjectMessage) enumeration.nextElement();
                DestinationDefinition destDef = (DestinationDefinition)destMsg.getObject();
                dests.add(destDef.name);
            }
        } catch (JMSException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dests;
    } 
    
    public List<String> getDestinations(){
        return getDestinations(ss.getSession().getDestinations());
    }
    
    public void send(Destination destination, String text) throws JMSException{
        send(destination, jms.createTextMessage(text));
    }
    
    protected void send(Destination destination, Message message) throws JMSException{
        TemporaryQueue receiver = jms.createTemporaryQueue();
        message.setJMSReplyTo(destination);
        JMSConsumer consumer = jms.createConsumer(receiver);
        jms.createProducer().send(destination, message);
        consumer.receive();
    }
}
