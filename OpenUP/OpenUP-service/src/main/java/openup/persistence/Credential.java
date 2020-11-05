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
    
    private Map<Long, Session> sessions;
    private Map<Long, Session> logonSessions;

    public Credential() {
        sessions = new ConcurrentHashMap<>();
        logonSessions = new ConcurrentHashMap<>();
    }

    @Override
    public void close() throws Exception {
        for(Session session : sessions.values()){
            session.close();
        }
        sessions.clear();
        
        for(Session session : logonSessions.values()){
            session.close();
        }
        logonSessions.clear();
    }
    
    public Session putSession(long timestamp, EntityManagerFactory factory, EntityManager manager){
        Session session = new Session(factory, manager);
        logonSessions.put(timestamp, session);
        return session;
    }
    
    public Session getSession(Principal principal){
        if(principal instanceof JsonWebToken){
            JsonWebToken jwt = (JsonWebToken)principal;
            logonSessions.computeIfPresent(jwt.getIssuedAtTime(), 
                    (time, session) -> { 
                        session.putConversation(jwt.getTokenID())
                                .putManager(jwt.getIssuedAtTime());
                        logonSessions.remove(jwt.getIssuedAtTime());
                        sessions.put(jwt.getIssuedAtTime(), session);
                        return session; 
                    }
            );
            return sessions.get(jwt.getIssuedAtTime());
        }
        else if(principal != null){
            long timestamp = 0;
            logonSessions.computeIfPresent(timestamp, 
                    (time, session) -> { 
                        session.putConversation(principal.getName())
                                .putManager(time);
                        logonSessions.remove(time);
                        sessions.put(time, session);
                        return session; 
                    }
            );
        }
        return null;
    }
    
    public Session removeSession(Principal principal){
        if(principal instanceof JsonWebToken){
            JsonWebToken jwt = (JsonWebToken)principal;
            sessions.computeIfPresent(
                    jwt.getIssuedAtTime(), 
                    (time, session) -> { 
                        session.removeConversation(jwt.getTokenID())
                                .removeManager(jwt.getIssuedAtTime())
                                .close();
                        return session; 
                    }
            );
            return sessions.remove(jwt.getIssuedAtTime());
        }
        return null;
    }
}
