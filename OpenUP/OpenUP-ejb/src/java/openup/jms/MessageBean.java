/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.QueueBrowser;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author FOXCONN
 */
//@Stateful
//@LocalBean
@SessionScoped
public class MessageBean implements MessageListener, Serializable {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Resource(lookup="java:comp/DefaultJMSConnectionFactory")
    ConnectionFactory factory;
    
    JMSContext jms;
    
    @Inject
    @Push(channel="message")
    //@SessionScoped
    PushContext push;
    
    @Resource(mappedName="java:app/OpenUP")
    Topic topic;
    
    @Inject
    Principal principal;
    
    TemporaryQueue queue;
    JMSConsumer consumer;
    JMSProducer producer;
    QueueBrowser browser;
    JMSConsumer topicConsumer;
    
    ConcurrentHashMap<String, Destination> destinations = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void postConstruct() {
        try {
            jms = factory.createContext();
            queue = jms.createTemporaryQueue();
            browser = jms.createBrowser(queue);
            consumer = jms.createConsumer(queue);
            //consumer.setMessageListener(this);
            producer = jms.createProducer();
            producer.setJMSReplyTo(queue);
            topicConsumer = jms.createConsumer(topic);
            //topicConsumer.setMessageListener(this);
            jms.start();
            Message msg = jms.createObjectMessage();
            msg.setStringProperty("callerPrincipalName", principal.getName());
            producer.send(topic, msg);
        } catch (Exception ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PreDestroy
    public void preDestroy() {
        try {
            browser.close();
            browser = null;
            consumer.close();
            consumer = null;
            producer.clearProperties();
            producer = null;
            queue.delete();
            queue = null;
            topicConsumer.close();
            topicConsumer = null;
            destinations.clear();
            destinations = null;
            jms.stop();
            jms.close();
            jms = null;
        } catch (JMSException ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage){
                onObjectMessage((ObjectMessage)message);
            }
            else if(message instanceof TextMessage){
                synchronized(push){
                    onTextMessage((TextMessage)message);
                }
            }
            message.acknowledge();
        } catch (JMSException ex) {
            Logger.getLogger(MessageBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void onObjectMessage(ObjectMessage message) throws JMSException {
        if(message.propertyExists("callerPrincipalName")){
            String callerPrincipalName = message.getStringProperty("callerPrincipalName");
            Destination destination = message.getJMSReplyTo();
            if(destination != null){
                destinations.put(callerPrincipalName, destination);
            }
        }
    }
    
    void onTextMessage(TextMessage message) throws JMSException {
        push.send(message.getText());
    }
    
    public List<String> getDestinations(){
        List<String> result = new ArrayList<>();
        destinations.keySet().forEach((key) -> {
            result.add(key);
        });
        return result;
    }
    
    public void send(String destination, String text){
        if(destinations.containsKey(destination)){
            Destination dest = destinations.get(destination);
            if(dest != null){
                synchronized(push){
                    TextMessage textMsg = jms.createTextMessage(text);
                    producer.send(dest, textMsg);
                    push.send(text);
                }
            }
        }
    }
}
