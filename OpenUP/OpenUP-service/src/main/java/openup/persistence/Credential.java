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
import epf.util.Var;

/**
 *
 * @author FOXCONN
 */
public class Credential implements AutoCloseable {
    
    private EntityManagerFactory factory;
    private EntityManager defaultManager;
    private Map<Long, Session> sessions;

    public Credential(EntityManagerFactory factory, EntityManager defaultManager) {
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

    @Override
    public void close() throws Exception {
    	Var<Exception> ex = new Var<>();
        sessions.forEach((time, session) -> {
        	try {
				session.close();
			} 
        	catch (Exception e) {
				ex.set(e);
			}
        });
        sessions.clear();
        defaultManager.close();
        factory.close();
    }
    
    public Session putSession(long timestamp){
        return sessions.computeIfAbsent(timestamp, time -> {
            return new Session(factory);
        });
    }
    
    public Session getSession(long timestamp){
        return sessions.get(timestamp);
    }
    
    public Session removeSession(long timestamp){
        return sessions.remove(timestamp);
    }
}
