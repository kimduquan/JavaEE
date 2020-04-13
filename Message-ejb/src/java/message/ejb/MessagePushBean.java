/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import message.event.MessageEvent;

/**
 *
 * @author FOXCONN
 */
@JMSDestinationDefinition(name = "java:app/Message", interfaceName = "javax.jms.Topic", resourceAdapter = "jmsra", destinationName = "Message")
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "java:app/Message"),
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:app/Message"),
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable"),
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "java:app/Message"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class MessagePushBean implements MessageListener {
    
    @Inject
    @Push(channel="message")
    private PushContext push;
    
    @Inject
    private JMSContext jms;
    
    @Inject
    private Event<MessageEvent> event;
    
    public MessagePushBean() {
    }
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof ObjectMessage){
                Destination replyTo = message.getJMSReplyTo();
                if(replyTo != null){
                    Message realMsg = null;
                    JMSConsumer consumer = jms.createConsumer(replyTo);
                    if(message.propertyExists("timeout")){
                        long timeout = message.getLongProperty("timeout");
                        realMsg = consumer.receive(timeout);
                    }
                    if(realMsg instanceof TextMessage){
                        TextMessage textMsg = (TextMessage)realMsg;
                        MessageEvent msgEvent = buildMessage(textMsg);
                        sendMessage(msgEvent);
                        event.fire(msgEvent);
                        jms.createProducer().send(replyTo, jms.createObjectMessage());
                    }
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(MessagePushBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    MessageEvent buildMessage(TextMessage msg) throws JMSException{
        MessageEvent msgEvent = new MessageEvent();
        if(msg.propertyExists("cid")){
            msgEvent.setCid(msg.getStringProperty("cid"));
        }
        if(msg.propertyExists("destination")){
            msgEvent.setDestination(msg.getStringProperty("destination"));
        }
        if(msg.propertyExists("replyTo")){
            msgEvent.setReplyTo(msg.getStringProperty("replyTo"));
        }
        msgEvent.setText(msg.getText());
        return msgEvent;
    }
    
    void sendMessage(MessageEvent msgEvent){
        if(msgEvent.getCid() != null && msgEvent.getCid().isEmpty() == false){
            push.send(msgEvent.getText(), msgEvent.getCid());
        }
        else if(msgEvent.getDestination() != null && msgEvent.getDestination().isEmpty() == false){
            push.send(msgEvent.getText(), msgEvent.getDestination());
        }
        else if(msgEvent.getReplyTo() != null && msgEvent.getReplyTo().isEmpty() == false){
            push.send(msgEvent.getText(), msgEvent.getReplyTo());
        }
    }
}
