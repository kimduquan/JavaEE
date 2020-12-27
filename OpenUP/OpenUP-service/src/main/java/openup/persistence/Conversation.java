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
    private Map<Long, EntityManager> managers;

    public Conversation(EntityManagerFactory factory) {
        this.factory = factory;
        managers = new ConcurrentHashMap<>();
    }
    
    public EntityManager getManager(long cid){
        return managers.get(cid);
    }
    
    public EntityManager putManager(long cid){
        EntityManager manager = factory.createEntityManager();
        managers.put(cid, manager);
        return manager;
    }
    
    public EntityManager removeManager(long cid){
        return managers.remove(cid);
    }

    @Override
    public void close() throws Exception {
        managers.values().forEach(manager -> {
            manager.close();
        });
        managers.clear();
    }
}
