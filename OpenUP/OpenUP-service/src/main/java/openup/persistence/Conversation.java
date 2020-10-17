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
public class Conversation implements AutoCloseable {
    
    private EntityManagerFactory factory;
    private Map<Long, EntityManager> conversations;

    public Conversation(EntityManagerFactory factory) {
        this.factory = factory;
        conversations = new ConcurrentHashMap<>();
    }
    
    public EntityManager getManager(long cid){
        return conversations.get(cid);
    }
    
    public EntityManager putManager(long cid){
        EntityManager manager = factory.createEntityManager();
        conversations.put(cid, manager);
        return manager;
    }
    
    public EntityManager removeManager(long cid){
        return conversations.remove(cid);
    }

    @Override
    public void close() throws Exception {
        conversations.values().forEach(manager -> {
            manager.close();
        });
        conversations.clear();
    }
}
