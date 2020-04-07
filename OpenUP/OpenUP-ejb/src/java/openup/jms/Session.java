/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class Session implements Serializable {
    
    @EJB
    SessionBean session;
    
    SessionBean cache;
    
    @PostConstruct
    public void postConstruct(){
        cache = session;
        cache.postConstruct();
    }
    
    @PreDestroy
    public void preDestroy(){
        cache.preDestroy();
    }
}
