/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Topic;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class ConversationBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @EJB
    private ContextBean context;
    
    private Topic conversation;
    private Destination destination;
    
    public void begin(String cid, Destination dest) throws JMSException{
        conversation = context.createTopic(cid);
        destination = dest;
    }
    
    public Topic getConversation(){
        return conversation;
    }
    
    public Destination getDestination(){
        return destination;
    }
}
