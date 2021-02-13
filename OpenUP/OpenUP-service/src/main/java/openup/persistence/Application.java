/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import epf.util.Var;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	private static final Logger logger = Logger.getLogger(Application.class.getName());
    
    private Map<String, Context> contexts;
    
    @PostConstruct
    void postConstruct(){
        contexts = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
    	Var<Exception> ex = new Var<>();
        contexts.values().forEach(context -> {
            try {
                context.close();
            } 
            catch (Exception e) {
            	ex.set(e);
            }
        });
        contexts.clear();
        ex.get(e -> {
        	if(e != null) {
            	logger.log(Level.INFO, e.getMessage(), e);
        	}
        });
    }
    
    public Context putContext(String unit) throws Exception{
        return contexts.computeIfAbsent(unit, name -> { return new Context();});
    }
    
    public Context getContext(String name){
        return contexts.get(name);
    }
}
