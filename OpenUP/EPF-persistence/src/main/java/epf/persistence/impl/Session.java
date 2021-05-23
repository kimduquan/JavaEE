/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
public class Session {
   
    /**
     * 
     */
    private transient final EntityManagerFactory factory;
    /**
     * 
     */
    private transient final Map<String, Conversation> conversations;

    /**
     * @param factory
     */
    public Session(final EntityManagerFactory factory) {
        this.factory = factory;
        conversations = new ConcurrentHashMap<>();
    }
    
    /**
     * @param sessionId
     * @return
     */
    public Conversation getConversation(final String sessionId){
        return conversations.get(sessionId);
    }
    
    /**
     * @param sessionId
     * @return
     */
    public Conversation putConversation(final String sessionId){
        return conversations.computeIfAbsent(sessionId, id -> {
            return new Conversation(factory);
        });
    }
    
    /**
     * @param sessionId
     * @return
     */
    public Conversation removeConversation(final String sessionId){
        return conversations.remove(sessionId);
    }

    /**
     * 
     */
    public void close() {
    	conversations.forEach((id, conversation) -> {
    		conversation.close();
        });
        conversations.clear();
    }
    
    /**
     * @param expirationTime
     * @return
     */
    public boolean checkExpirationTime(final long expirationTime) {
    	return System.currentTimeMillis() < expirationTime * 1000;
    }
}
