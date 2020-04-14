/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 *
 * @author FOXCONN
 */
public abstract class TemporaryListener implements MessageListener {

    @Inject
    private JMSContext jms;

    @Override
    public void onMessage(Message message) {
        try {
            onRealMessage(message, getRealMessage(message));
        } catch (JMSException ex) {
            Logger.getLogger(QueueListenerBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected Message getRealMessage(Message message) throws JMSException{
        Destination replyTo = message.getJMSReplyTo();
        JMSConsumer consumer = jms.createConsumer(replyTo);
        Message realMsg = null;
        if(message.propertyExists("receive_timeout")){
            long timeout = message.getLongProperty("receive_timeout");
            realMsg = consumer.receive(timeout);
        }
        else{
            realMsg = consumer.receive();
        }
        return realMsg;
    }
    
    protected abstract void onRealMessage(Message tempMsg, Message realMsg) throws JMSException;
}
