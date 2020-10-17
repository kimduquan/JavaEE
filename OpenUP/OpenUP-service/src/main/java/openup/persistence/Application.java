/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    @PersistenceContext(name = "EPF", unitName = "EPF")
    private EntityManager defaultManager;
    
    private Map<String, Session> sessions;
    
    @PostConstruct
    void postConstruct(){
        sessions = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
        sessions.values().forEach(s -> {
            try {
                s.close();
            } 
            catch (Exception ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public boolean createFactory(String userName, String password){
        boolean hasExist = sessions.containsKey(userName);
    	if(!hasExist){
            Map<String, Object> props = new HashMap<>();
            props.put("javax.persistence.jdbc.user", userName);
            props.put("javax.persistence.jdbc.password", password);            
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("OpenUP", props);
            EntityManager manager = factory.createEntityManager();
            Session session = new Session(factory, manager);
            sessions.put(userName, session);
        }
        return !hasExist;
    }
    
    public EntityManager getDefaultManager(){
        return defaultManager;
    }
    
    public Session getSession(String userName){
        return sessions.get(userName);
    }
}
