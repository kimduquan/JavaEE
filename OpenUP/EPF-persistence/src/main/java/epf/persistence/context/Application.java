/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	/**
	 * 
	 */
	private transient final Map<String, Context> contexts;
    
    /**
     * 
     */
    @Inject
    private transient Logger logger;
    
    /**
     * 
     */
    public Application() {
    	contexts = new ConcurrentHashMap<>();
    }
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
    	contexts.values().forEach(context -> {
            try {
                context.close();
            } 
            catch (Exception e) {
            	logger.throwing(Context.class.getName(), "close", e);
            }
        });
        contexts.clear();
    }
    
    /**
     * @param unit
     * @return
     */
    public Context putContext(final String unit){
        return contexts.computeIfAbsent(unit, name -> { return new Context();});
    }
    
    /**
     * @param name
     * @return
     */
    public Context getContext(final String name){
        return contexts.get(name);
    }
}
