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
import javax.persistence.EntityManagerFactory;
import epf.util.Var;

/**
 *
 * @author FOXCONN
 */
public class Session implements AutoCloseable {
	
	private static final Logger logger = Logger.getLogger(Session.class.getName());
   
    private EntityManagerFactory factory;
    private Map<String, Conversation> conversations;

    public Session(EntityManagerFactory factory) {
        this.factory = factory;
        conversations = new ConcurrentHashMap<>();
    }
    
    public Conversation getConversation(String sessionId){
        return conversations.get(sessionId);
    }
    
    public Conversation putConversation(String sessionId){
        return conversations.computeIfAbsent(sessionId, id -> {
            return new Conversation(factory);
        });
    }
    
    public Conversation removeConversation(String sessionId){
        return conversations.remove(sessionId);
    }

    @Override
    public void close() throws Exception {
    	Var<Exception> error = new Var<>();
    	conversations.forEach((id, conversation) -> {
        	try {
				conversation.close();
			} 
        	catch (Exception e) {
        		logger.log(Level.WARNING, e.getMessage(), e);
        		error.set(e);
			}
        });
        conversations.clear();
        if(error.get() != null) {
        	throw error.get();
        }
    }
    
    public boolean checkExpirationTime(long expirationTime) {
    	return System.currentTimeMillis() < expirationTime * 1000;
    }
}
