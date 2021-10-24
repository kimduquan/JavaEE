/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.internal.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
public class Conversation {
    
    /**
     * 
     */
    private transient final EntityManagerFactory factory;
    /**
     * 
     */
    private transient final Map<Long, EntityManager> managers;

    /**
     * @param factory
     */
    public Conversation(final EntityManagerFactory factory) {
        this.factory = factory;
        managers = new ConcurrentHashMap<>();
    }
    
    /**
     * @param cid
     * @return
     */
    public EntityManager getManager(final long cid){
        return managers.get(cid);
    }
    
    /**
     * @param cid
     * @return
     */
    public EntityManager putManager(final long cid){
        return managers.computeIfAbsent(cid, id -> {
            return factory.createEntityManager();
        });
    }
    
    /**
     * @param cid
     * @return
     */
    public EntityManager removeManager(final long cid){
        return managers.remove(cid);
    }

    /**
     *
     */
    public void close() {
        managers.values().forEach(manager -> {
            manager.close();
        });
        managers.clear();
    }
}
