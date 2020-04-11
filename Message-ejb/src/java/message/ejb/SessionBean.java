/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.QueueBrowser;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;
import message.Session;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class SessionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Resource
    private ConnectionFactory factory;
    
    private JMSContext jms;
    
    @Resource(mappedName="java:app/Message")
    private Topic messages;
    
    @Resource(mappedName="java:app/Destination")
    private Topic destinations;
    
    @Inject
    private Principal principal;
    
    private TemporaryQueue queue;
    private JMSConsumer destinationsConsumer;
    private JMSProducer producer;
    private QueueBrowser destinationBrowser;
    private JMSConsumer messagesConsumer;
    
    @PostConstruct
    void postConstruct() {
        try {
            jms = factory.createContext();
            queue = jms.createTemporaryQueue();
            destinationBrowser = jms.createBrowser(queue);
            destinationsConsumer = jms.createConsumer(queue);
            producer = jms.createProducer();
            producer.setJMSReplyTo(queue);
            messagesConsumer = jms.createConsumer(messages);
            
        } catch (Exception ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start(){
        try {
            jms.start();
            ObjectMessage msg = jms.createObjectMessage();
            msg.setStringProperty("callerPrincipalName", principal.getName());
            producer.send(destinations, msg);
        } catch (JMSException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Remove
    public void remove(){
        try {
            destinationBrowser.close();
            destinationsConsumer.close();
            producer.clearProperties();
            queue.delete();
            messagesConsumer.close();
            jms.stop();
            jms.close();
        } catch (JMSException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PreDestroy
    void preDestroy() {
        destinationBrowser = null;
        destinationsConsumer = null;
        producer = null;
        queue = null;
        messagesConsumer = null;
        jms = null;
    }
    
    public QueueBrowser getBrowser(){
        return destinationBrowser;
    }
    
    public JMSProducer getProducer(){
        return producer;
    }
    
    public JMSConsumer getConsumer(){
        return destinationsConsumer;
    }
}
