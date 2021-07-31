/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.context;

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
    private transient final Map<String, Session> sessions;

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
    public Session putSession(final String sessionId){
        return sessions.computeIfAbsent(sessionId, time -> {
            return new Session(factory);
        });
    }
    
    /**
     * @param timestamp
     * @return
     */
    public Session getSession(final String sessionId){
        return sessions.get(sessionId);
    }
    
    /**
     * @param timestamp
     * @return
     */
    public Session removeSession(final String sessionId){
        return sessions.remove(sessionId);
    }
}
