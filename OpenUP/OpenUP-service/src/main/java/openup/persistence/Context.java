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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import epf.util.Var;

/**
 *
 * @author FOXCONN
 */
public class Context implements AutoCloseable {
	
	private static final Logger logger = Logger.getLogger(Context.class.getName());
    
    private Map<String, Credential> credentials;
    
    public Context(){
        credentials = new ConcurrentHashMap<>();
    }
    
    public Credential putCredential(String userName, String unit, String password_hash) throws Exception{
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", userName);
        props.put("javax.persistence.jdbc.password", password_hash);
        Var<Exception> error = new Var<>();
        credentials.computeIfAbsent(userName, name -> {
            return newCredential(unit, props, error);
        });
        if(error.get() != null){
			logger.throwing(getClass().getName(), "putCredential", error.get());
            throw error.get();
        }
        Credential cred = credentials.computeIfPresent(userName, (name, credential) -> {
            if(credential != null){
                synchronized(credential){
                    if(!props.equals(credential.getFactory().getProperties())){
                        try {
                            credential.close();
                        } 
                        catch (Exception ex) {
                            logger.log(Level.WARNING, ex.getMessage(), ex);
                        }
                        credential = newCredential(unit, props, error);
                    }
                }
            }
            else{
                credential = newCredential(unit, props, error);
            }
            return credential;
        });
        if(error.get() != null){
        	logger.throwing(getClass().getName(), "putCredential", error.get());
            throw error.get();
        }
        return cred;
    }
    
    Credential newCredential(String unit, Map<String, Object> props, Var<Exception> error){
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try{
            factory = Persistence.createEntityManagerFactory(unit, props);
            manager = factory.createEntityManager();
            return new Credential(factory, manager);
        }
        catch (Exception ex) {
            error.set(ex);
            if(manager != null){
                manager.close();
            }
            if(factory != null){
                factory.close();
            }
        }
        return null;
    }
    
    public Credential getCredential(String userName){
        return credentials.get(userName);
    }
    
    public Credential removeCredential(String userName){
        return credentials.remove(userName);
    }

    @Override
    public void close() throws Exception {
    	Var<Exception> ex = new Var<>();
    	credentials.forEach((name, credential) -> {
			try {
				credential.close();
			} 
			catch (Exception e) {
				ex.set(e);
			}
		});
        credentials.clear();
    }
}
