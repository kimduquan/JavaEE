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

    @Override
    public void close() throws Exception {
        for(Session session : sessions.values()){
            session.close();
        }
        sessions.clear();
        defaultManager.clear();
        defaultManager.close();
        factory.getCache().evictAll();
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
    
    public void reset(EntityManagerFactory factory, EntityManager defaultManager){
        this.factory = factory;
        this.defaultManager = defaultManager;
        sessions.clear();
    }
}
