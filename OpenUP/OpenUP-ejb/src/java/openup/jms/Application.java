/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.jms;

import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.Destination;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    ConcurrentHashMap<String, Destination> destinations = new ConcurrentHashMap<>();
    
    @PostConstruct
    void postConstruct(){
        
    }
    
    @PreDestroy
    void preDestroy(){
        
    }
}
