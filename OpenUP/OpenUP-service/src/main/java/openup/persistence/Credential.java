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
    private Map<Long, Session> loginSessions;

    public Credential() {
        sessions = new ConcurrentHashMap<>();
        loginSessions = new ConcurrentHashMap<>();
    }

    @Override
    public void close() throws Exception {
        for(Session session : sessions.values()){
            session.close();
        }
        sessions.clear();
        
        for(Session session : loginSessions.values()){
            session.close();
        }
        loginSessions.clear();
    }
    
    public Session putSession(long timestamp, EntityManagerFactory factory, EntityManager manager){
        Session session = new Session(factory, manager);
        loginSessions.put(timestamp, session);
        return session;
    }
    
    public Session getSession(Principal principal){
        if(principal instanceof JsonWebToken){
            JsonWebToken jwt = (JsonWebToken)principal;
            loginSessions.computeIfPresent(
                    jwt.getIssuedAtTime(), 
                    (time, session) -> { 
                        session.putConversation(jwt.getTokenID())
                                .putManager(jwt.getIssuedAtTime());
                        loginSessions.remove(jwt.getIssuedAtTime());
                        sessions.put(jwt.getIssuedAtTime(), session);
                        return session; 
                    }
            );
            return sessions.get(jwt.getIssuedAtTime());
        }
        else{
            long timestamp = 0;
            loginSessions.computeIfPresent(
                    timestamp, 
                    (time, session) -> { 
                        session.putConversation(principal.getName())
                                .putManager(time);
                        loginSessions.remove(time);
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
