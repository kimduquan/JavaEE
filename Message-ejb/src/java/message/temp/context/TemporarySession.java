/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.temp.context;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import message.temp.SessionBean;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class TemporarySession implements Serializable {
    
    @EJB
    private SessionBean session;
    
    @PostConstruct
    void postConstruct(){
        session.start(6000);
    }
    
    @PreDestroy
    void preDestroy(){
        session.remove();
    }
}
