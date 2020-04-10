/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import message.ejb.SessionBean;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

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
    
    @PreDestroy
    void preDestroy(){
        session.remove();
    }
    
    public SessionBean getSession(){
        return session;
    }
}
