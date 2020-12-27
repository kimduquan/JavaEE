/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
public class Session implements AutoCloseable {
   
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
        Conversation conversation = new Conversation(factory);
        conversations.put(sessionId, conversation);
        return conversation;
    }
    
    public Conversation removeConversation(String sessionId){
        return conversations.remove(sessionId);
    }

    @Override
    public void close() throws Exception {
        for(Conversation conversation : conversations.values()){
            conversation.close();
        }
        conversations.clear();
    }
}
