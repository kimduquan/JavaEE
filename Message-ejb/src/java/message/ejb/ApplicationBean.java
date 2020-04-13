/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message.ejb;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.annotation.PreDestroy;
import javax.ejb.AccessTimeout;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;

/**
 *
 * @author FOXCONN
 */
@Singleton
@LocalBean
@AccessTimeout(value=6, unit=TimeUnit.MINUTES)
public class ApplicationBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private ConcurrentHashMap<String, Destination> destinations = new ConcurrentHashMap<>();
    
    @Inject
    private JMSContext jms;
    
    @PreDestroy
    void preDestroy(){
        destinations.clear();
        destinations = null;
    }
    
    @Lock(LockType.READ)
    public Destination getDestination(String principalName){
        return destinations.getOrDefault(principalName, null);
    }
    
    @Lock(LockType.READ)
    public HashMap<String, Destination> getDestinations(String principalName){
        HashMap<String, Destination> dests = new HashMap<>();
        dests.putAll(destinations);
        dests.remove(principalName);
        return dests;
    }
    
    @Lock(LockType.WRITE)
    public Destination createDestination(String name){
        Destination destination = null;
        if(!destinations.containsKey(name)){
            destination = jms.createTemporaryQueue();
            destinations.put(name, destination);
        }
        else{
            destination = destinations.get(name);
        }
        return destination;
    }
}
