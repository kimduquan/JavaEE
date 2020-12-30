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
    
    private Map<String, Context> contexts;
    
    @PostConstruct
    void postConstruct(){
        contexts = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
        contexts.values().forEach(context -> {
            try {
                context.close();
            } 
            catch (Exception ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        contexts.clear();
    }
    
    public Context putContext(String unit, String userName, String password, long timestamp) throws Exception{
        contexts.computeIfPresent(unit, (name, context) -> {
            if(context != null){
                return context;
            }
            return new Context();
        });
        Context context = contexts.computeIfAbsent(unit, key -> {
            return new Context();
        });
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", userName);
        props.put("javax.persistence.jdbc.password", password);            
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(unit, props);
        EntityManager manager = factory.createEntityManager();
        context.putCredential(userName, factory, manager);
        return context;
    }
    
    public EntityManager getDefaultManager(){
        return defaultManager;
    }
    
    public Context getContext(String name){
        return contexts.get(name);
    }
}
