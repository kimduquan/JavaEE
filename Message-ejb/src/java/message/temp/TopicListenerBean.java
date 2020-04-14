/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import java.util.Enumeration;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;

/**
 *
 * @author FOXCONN
 */
@Stateless
@LocalBean
public class TopicListenerBean extends TemporaryListener {
    
    @Inject
    private JMSContext jms;

    @Override
    protected void onRealMessage(Message tempMsg, Message realMsg) throws JMSException {
        Queue queue = (Queue) realMsg.getJMSReplyTo();
        QueueBrowser browser = null;
        JMSConsumer consumer = null;
        if(realMsg.propertyExists("browser_selector")){
            String selector = realMsg.getStringProperty("browser_selector");
            browser = jms.createBrowser(queue, selector);
            consumer = jms.createConsumer(queue, selector);
        }
        else{
            browser = jms.createBrowser(queue);
            consumer = jms.createConsumer(queue);
        }
        
        Enumeration messages = browser.getEnumeration();
        long size = 0;
        while(messages.hasMoreElements()){
            messages.nextElement();
            size++;
        }
        
        boolean hasTimeout = realMsg.propertyExists("receive_tmeout");
        long timeout = 0;
        if(hasTimeout){
            timeout = realMsg.getLongProperty("receive_tmeout");
        }
        JMSProducer producer = jms.createProducer();
        for(int i = 0; i < size; i++){
            Message msg = null;
            if(hasTimeout){
                msg = consumer.receive(timeout);
            }
            else{
                msg = consumer.receive();
            }
            if(msg != null){
                Destination dest = msg.getJMSReplyTo();
                if(dest != null){
                    msg.setJMSReplyTo(null);
                    producer.send(dest, msg);
                }
            }
        }
    }
    
}
