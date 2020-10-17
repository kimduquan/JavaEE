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
    
    private Map<String, Credential> credentials;
    
    @PostConstruct
    void postConstruct(){
        credentials = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
        credentials.values().forEach(credential -> {
            try {
                credential.close();
            } 
            catch (Exception ex) {
                Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        credentials.clear();
    }
    
    public Credential putCredential(String userName, String password, long timestamp){
        Credential credential = credentials.get(userName);
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", userName);
        props.put("javax.persistence.jdbc.password", password);            
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("OpenUP", props);
        EntityManager manager = factory.createEntityManager();
        if(credential == null){
            credential = new Credential();
            credentials.put(userName, credential);
        }
        credential.putSession(timestamp, factory, manager);
        return credential;
    }
    
    public EntityManager getDefaultManager(){
        return defaultManager;
    }
    
    public Credential getCredential(String name){
        return credentials.get(name);
    }
}
