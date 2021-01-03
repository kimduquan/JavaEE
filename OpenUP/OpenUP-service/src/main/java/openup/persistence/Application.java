/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        List<Exception> errors = new ArrayList<>();
        contexts.computeIfPresent(unit, (name, context) -> {
            try{
                EntityManagerFactory factory = Persistence.createEntityManagerFactory(unit, props);
                EntityManager manager = factory.createEntityManager();
                context.putCredential(userName, factory, manager);
            } 
            catch (Exception ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                errors.add(ex);
            }
            return context;
        });
        if(!errors.isEmpty()){
            throw errors.get(0);
        }
        Context ctx = contexts.computeIfAbsent(unit, name -> {
            Context context = new Context();
            try{
                EntityManagerFactory factory = Persistence.createEntityManagerFactory(unit, props);
                EntityManager manager = factory.createEntityManager();
                context.putCredential(userName, factory, manager);
            }
            catch(Exception ex){
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
                errors.add(ex);
            }
            return context;
        });
        if(!errors.isEmpty()){
            throw errors.get(0);
        }
        return ctx;
    }
    
    public Context getContext(String name){
        return contexts.get(name);
    }
}
