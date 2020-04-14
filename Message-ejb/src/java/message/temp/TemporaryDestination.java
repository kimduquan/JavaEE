/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 *
 * @author FOXCONN
 */
public abstract class TemporaryDestination implements MessageListener {
    
    @EJB
    private DestinationsBean app;
    
    @Inject
    private JMSContext jms;
    
    @Inject
    private Event<DestinationDefinition> destinationEvent;
    
    @Override
    public void onMessage(Message message) {
        try {
            if(message != null){
                boolean isCreateQueueMsg = false;
                if(message instanceof ObjectMessage){
                    ObjectMessage objMsg = (ObjectMessage)message;
                    if(objMsg.getObject() instanceof DestinationDefinition){
                        isCreateQueueMsg = true;
                        onCreateMessage(objMsg);
                        destinationEvent.fire((DestinationDefinition)objMsg.getObject());
                    }
                }
                if(!isCreateQueueMsg){
                    if(message.getJMSReplyTo() != null){
                        onTemporaryMessage(message);
                    }
                }
            }
        } catch (JMSException ex) {
            Logger.getLogger(TemporaryQueueBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void onCreateMessage(ObjectMessage message) throws JMSException{
        DestinationDefinition destDef = (DestinationDefinition) message.getObject();
        Destination replyTo = message.getJMSReplyTo();
        if(replyTo != null){
            Destination dest = createTemporayDestination(destDef);
            ObjectMessage replyMsg = jms.createObjectMessage();
            replyMsg.setJMSReplyTo(dest);
            jms.createProducer().send(replyTo, replyMsg);
        }
    }
    
    protected Destination createTemporayDestination(DestinationDefinition destDef) throws JMSException{
        if("javax.jms.Queue".equals(destDef.interfaceName)){
            return app.createTemporaryQueue(destDef.name);
        }
        else if("javax.jms.Topic".equals(destDef.interfaceName)){
            return app.createTemporaryTopic(destDef.name);
        }
        return null;
    }
    
    protected abstract void onTemporaryMessage(Message message) throws JMSException;
}
