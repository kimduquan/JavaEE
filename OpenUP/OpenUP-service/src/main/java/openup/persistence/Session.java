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
    
    public Conversation getConversation(String sessionId){
        return sessions.get(sessionId);
    }
    
    public Conversation putConversation(String sessionId){
        Conversation conversation = new Conversation(factory);
        sessions.put(sessionId, conversation);
        return conversation;
    }
    
    public Conversation removeConversation(String sessionId){
        return sessions.remove(sessionId);
    }

    @Override
    public void close() throws Exception {
        for(Conversation conversation : sessions.values()){
            conversation.close();
        }
        sessions.clear();
        defaultManager.close();
        factory.close();
    }
}
