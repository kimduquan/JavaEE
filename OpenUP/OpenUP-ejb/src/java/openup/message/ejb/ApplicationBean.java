/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.message.ejb;

import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.jms.Destination;

/**
 *
 * @author FOXCONN
 */
@Singleton
@LocalBean
public class ApplicationBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private ConcurrentHashMap<String, Destination> destinations = new ConcurrentHashMap<>();
    
    @PreDestroy
    void preDestroy(){
        destinations.clear();
        destinations = null;
    }
    
    public ConcurrentHashMap<String, Destination> getDestinations(){
        return destinations;
    }
}
