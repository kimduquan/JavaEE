/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.jms.JMSException;
import javax.jms.Queue;

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
    
    private Queue queue;
    
    public void begin(String name, long timeout){
        try {
            queue = context.createQueue(name, timeout);
        } catch (JMSException ex) {
            Logger.getLogger(ConversationBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void end(){
        queue = null;
    }
}
