/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
public class Credential {
    
    /**
     * 
     */
    private transient final EntityManagerFactory factory;
    /**
     * 
     */
    private transient final EntityManager defaultManager;
    /**
     * 
     */
    private transient final Map<Long, Session> sessions;

    /**
     * @param factory
     * @param defaultManager
     */
    public Credential(final EntityManagerFactory factory, final EntityManager defaultManager) {
        this.factory = factory;
        this.defaultManager = defaultManager;
        sessions = new ConcurrentHashMap<>();
    }
    
    public EntityManagerFactory getFactory(){
        return factory;
    }
    
    public EntityManager getDefaultManager() {
    	return defaultManager;
    }

    /**
     * 
     */
    public void close() {
    	sessions.forEach((time, session) -> {
    		session.close();
        });
        sessions.clear();
        defaultManager.close();
        factory.close();
    }
    
    /**
     * @param timestamp
     * @return
     */
    public Session putSession(final long timestamp){
        return sessions.computeIfAbsent(timestamp, time -> {
            return new Session(factory);
        });
    }
    
    /**
     * @param timestamp
     * @return
     */
    public Session getSession(final long timestamp){
        return sessions.get(timestamp);
    }
    
    /**
     * @param timestamp
     * @return
     */
    public Session removeSession(final long timestamp){
        return sessions.remove(timestamp);
    }
}
