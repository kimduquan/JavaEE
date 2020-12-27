/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.eclipse.microprofile.jwt.JsonWebToken;

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
    
    public EntityManager getDefaultManager(){
        return defaultManager;
    }

    @Override
    public void close() throws Exception {
        for(Session session : sessions.values()){
            session.close();
        }
        sessions.clear();
    }
    
    public Session putSession(long timestamp){
        Session session = new Session(factory);
        sessions.put(timestamp, session);
        return session;
    }
    
    public Session getSession(Principal principal){
        if(principal instanceof JsonWebToken){
            JsonWebToken jwt = (JsonWebToken)principal;
            return sessions.computeIfAbsent(
                    jwt.getIssuedAtTime(), 
                    (time) -> { 
                        Session session = new Session(factory);
                        session.putConversation(jwt.getTokenID())
                                .putManager(jwt.getIssuedAtTime());
                        return session; 
                    }
            );
        }
        else if(principal != null){
            long timestamp = 0;
            return sessions.computeIfAbsent(
                    timestamp, 
                    (time) -> { 
                        Session session = new Session(factory);
                        session.putConversation(principal.getName())
                                .putManager(time);
                        return session; 
                    }
            );
        }
        return null;
    }
    
    public Session removeSession(Principal principal){
        if(principal instanceof JsonWebToken){
            JsonWebToken jwt = (JsonWebToken)principal;
            return sessions.remove(jwt.getIssuedAtTime());
        }
        else if(principal != null){
            long timestamp = 0;
            return sessions.remove(timestamp);
        }
        return null;
    }
}
