/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	private Map<String, Context> contexts;
    
    @Inject
    private Logger logger;
    
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
            catch (Exception e) {
            	logger.log(Level.WARNING, e.getMessage(), e);
            }
        });
        contexts.clear();
    }
    
    public Context putContext(String unit) throws Exception{
        return contexts.computeIfAbsent(unit, name -> { return new Context();});
    }
    
    public Context getContext(String name){
        return contexts.get(name);
    }
}
