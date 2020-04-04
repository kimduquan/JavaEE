/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.servlet.http;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author FOXCONN
 */
@WebListener
public class MessageListener implements HttpSessionListener {
    
    @Inject
    JMSContext jms;
    
    @Resource(lookup="java:app/OpenUP")
    Topic topic;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        try {
            ObjectMessage msg = jms.createObjectMessage();
            msg.setStringProperty("name", "sessionCreated");
            msg.setStringProperty("sessionId", se.getSession().getId());
            jms.createProducer().send(topic, msg);
        } catch (JMSException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        try {
            ObjectMessage msg = jms.createObjectMessage();
            msg.setStringProperty("name", "sessionDestroyed");
            msg.setStringProperty("sessionId", se.getSession().getId());
            jms.createProducer().send(topic, msg);
        } catch (JMSException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
