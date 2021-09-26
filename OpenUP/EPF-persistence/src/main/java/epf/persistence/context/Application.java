/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.context;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.delivery_processes.schema.DeliveryProcesses;
import epf.roles.schema.Roles;
import epf.tasks.schema.Tasks;
import epf.work_products.schema.WorkProducts;
import openup.schema.OpenUP;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	/**
	 * 
	 */
	private transient final Map<String, Context> contexts = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient Context defaultContext;
    
    /**
     * 
     */
    @Inject
    private transient Logger logger;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct() {
    	defaultContext = new Context("EPF");
    	contexts.put(DeliveryProcesses.SCHEMA, new Context(DeliveryProcesses.SCHEMA));
    	contexts.put(Roles.SCHEMA, new Context(Roles.SCHEMA));
    	contexts.put(Tasks.SCHEMA, new Context(Tasks.SCHEMA));
    	contexts.put(WorkProducts.SCHEMA, new Context(WorkProducts.SCHEMA));
    	contexts.put(OpenUP.SCHEMA, new Context(OpenUP.SCHEMA));
    }
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
    	defaultContext.close();
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
     * @param name
     * @return
     */
    public Context getContext(final String name){
        return contexts.get(name);
    }
    
    public Collection<Context> getContexts(){
    	return contexts.values();
    }
    
    public Context getDefaultContext() {
    	return defaultContext;
    }
}
