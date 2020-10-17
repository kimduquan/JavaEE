/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
public class Session implements AutoCloseable {
   
    private EntityManagerFactory factory;
    private EntityManager defaultManager;
    private Map<String, Conversation> sessions;

    public Session(EntityManagerFactory factory, EntityManager defaultManager) {
        this.factory = factory;
        this.defaultManager = defaultManager;
        sessions = new ConcurrentHashMap<>();
    }

    public EntityManager getDefaultManager() {
        return defaultManager;
    }
    
    public Conversation getSession(String sessionId){
        return sessions.get(sessionId);
    }
    
    public Conversation putSession(String sessionId){
        Conversation conversation = new Conversation(factory);
        sessions.put(sessionId, conversation);
        return conversation;
    }

    @Override
    public void close() throws Exception {
        sessions.values().forEach(
                conversation -> {
                    try {
                        conversation.close();
                    } 
                    catch (Exception ex) {
                        Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        );
        sessions.clear();
        defaultManager.close();
        factory.close();
    }
}
