/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.QueueBrowser;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class Session implements Serializable {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Resource(lookup="java:comp/DefaultJMSConnectionFactory")
    ConnectionFactory factory;
    
    JMSContext jms;
    
    @Resource(mappedName="java:app/OpenUP")
    Topic topic;
    
    @Inject
    Principal principal;
    
    TemporaryQueue queue;
    JMSConsumer consumer;
    JMSProducer producer;
    QueueBrowser browser;
    JMSConsumer topicConsumer;
    
    @PostConstruct
    void postConstruct() {
        try {
            jms = factory.createContext();
            queue = jms.createTemporaryQueue();
            browser = jms.createBrowser(queue);
            consumer = jms.createConsumer(queue);
            producer = jms.createProducer();
            producer.setJMSReplyTo(queue);
            topicConsumer = jms.createConsumer(topic);
            jms.start();
            Message msg = jms.createObjectMessage();
            msg.setStringProperty("callerPrincipalName", principal.getName());
            producer.send(topic, msg);
        } catch (Exception ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PreDestroy
    void preDestroy() {
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
            jms.stop();
            jms.close();
            jms = null;
        } catch (JMSException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
