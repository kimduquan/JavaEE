/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.security.Principal;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import message.event.MessageEvent;
import message.context.Session;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class ConversationBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private ArrayList<MessageEvent> messages = new ArrayList<>();
    private String destination;
    private String replyTo;
    private String cid;
    private Destination jmsDestination;
    
    @Inject
    private Session session;
    
    @Inject
    private Principal principal;
    
    @Inject
    private JMSContext jms;
    
    @Resource(mappedName="")
    private Topic destinations;
    
    public void onNewMessage(@Observes(notifyObserver=Reception.IF_EXISTS) MessageEvent event){
        if(destination.equals(event.getDestination()) && replyTo.equals(event.getReplyTo())){
            messages.add(event.copy());
        }
        else if(destination.equals(event.getReplyTo()) && replyTo.equals(event.getDestination())){
            messages.add(event.copy());
        }
    }
    
    public void begin(String destination, String cid) throws JMSException{
        this.destination = destination;
        this.replyTo = principal.getName();
        this.cid = cid;
        this.jmsDestination = session.getSession().getDestination(destination);
        
        ObjectMessage msg = jms.createObjectMessage();
        msg.setStringProperty("destination", destination);
        msg.setStringProperty("replyTo", replyTo);
        msg.setStringProperty("cid", cid);
        Destination temp = jms.createTemporaryQueue();
        msg.setJMSReplyTo(temp);
        jms.createProducer().send(destinations, msg);
        Message replyMsg = jms.createConsumer(temp).receive();
        
    }
    
    public void send(String text){
        
    }
}
