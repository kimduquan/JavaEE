/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
public class Session implements AutoCloseable {

    public Session(EntityManagerFactory factory, EntityManager defaultManager) {
        this.factory = factory;
        this.defaultManager = defaultManager;
        sessions = new ConcurrentHashMap<>();
    }
   
    private EntityManagerFactory factory;
    private EntityManager defaultManager;
    private Map<String, EntityManager> sessions;
    
    public EntityManager getManager(String sessionId){
        EntityManager manager = null;
        if(!sessions.containsKey(sessionId)){
            manager = factory.createEntityManager();
            sessions.put(sessionId, manager);
        }
        return manager;
    }
    
    public EntityManagerFactory getFactory(){
        return factory;
    }

    @Override
    public void close() throws Exception {
        sessions.values().forEach(manager -> {
            manager.close();
        });
        sessions.clear();
        defaultManager.close();
        factory.close();
    }
}
