/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.context;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
	
	/**
	 * 
	 */
	private transient Context defaultContext;
    
    /**
     * 
     */
    @PostConstruct
    protected void postConstruct() {
    	defaultContext = new Context(EPF.SCHEMA);
    }
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
    	defaultContext.close();
    }
    
    public Context getDefaultContext() {
    	return defaultContext;
    }
}
