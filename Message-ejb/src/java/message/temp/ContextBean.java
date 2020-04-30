/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import message.DestinationDefinition;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;

/**
 *
 * @author FOXCONN
 */
@Stateless
@LocalBean
public class ContextBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Resource(mappedName="java:app/TemporaryQueue")
    private Queue temporaryQueue;
    
    @Resource(mappedName="java:app/TemporaryTopic")
    private Topic temporaryTopic;
    
    @Inject
    private JMSContext jms;
    
    public Queue createQueue(String name, long timeout) throws JMSException{
        
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
        
        jms.createProducer().send(temporaryQueue, createMsg);
        
        Message replyMsg = consumer.receive(timeout);
        Queue queue = (Queue) replyMsg.getJMSReplyTo();
        return queue;
    }
    
    public Topic createTopic(String name, long timeout) throws JMSException{
        
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
        createMsg.setStringProperty("destinationType", destDef.interfaceName);
        
        jms.createProducer().send(temporaryTopic, createMsg);
        
        Message replyMsg = consumer.receive(timeout);
        Topic topic = (Topic) replyMsg.getJMSReplyTo();
        return topic;
    }
}
