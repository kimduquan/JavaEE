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
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", userName);
        props.put("javax.persistence.jdbc.password", password);            
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(unit, props);
        EntityManager manager = factory.createEntityManager();
        contexts.computeIfPresent(unit, (name, context) -> {
            if(context != null){
                try {
                    context.close();
                } 
                catch (Exception ex) {
                    Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            context = new Context();
            context.putCredential(userName, factory, manager);
            return context;
        });
        return contexts.computeIfAbsent(unit, key -> {
            Context context = new Context();
            context.putCredential(userName, factory, manager);
            return context;
        });
    }
    
    public Context getContext(String name){
        return contexts.get(name);
    }
}
