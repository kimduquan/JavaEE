/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import javax.ejb.Remove;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Queue;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
public class SessionBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Inject
    private ContextBean context;
    
    @Inject
    private Principal principal;
    
    private Queue destinations;
    
    public void start() {
        try {
            destinations = context.createQueue(principal.getName());
        } catch (JMSException ex) {
            Logger.getLogger(SessionBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Remove
    public void remove(){
        destinations = null;
    }
    
    public Queue getDestinations(){
        return destinations;
    }
}
