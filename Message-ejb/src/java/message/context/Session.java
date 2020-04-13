/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.context;

import message.ejb.SessionBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.jms.JMSException;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class Session implements Serializable {
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private SessionBean session;
    
    @PostConstruct
    void postConstruct(){
        try {
            session.start();
        } catch (JMSException ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @PreDestroy
    void preDestroy(){
        session.remove();
    }
    
    public SessionBean getSession(){
        return session;
    }
}
