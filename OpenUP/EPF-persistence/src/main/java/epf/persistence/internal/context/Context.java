/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.internal.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import epf.util.EPFException;
import epf.util.Var;

/**
 *
 * @author FOXCONN
 */
public class Context {
	
	/**
	 * 
	 */
	private transient final String name;
    
    /**
     * 
     */
    private transient final Map<String, Credential> credentials;
    
    /**
     * @param name
     */
    public Context(final String name){
        this.name = name;
		credentials = new ConcurrentHashMap<>();
    }
    
    /**
     * @param userName
     * @param passwordHash
     * @return
     * @throws EPFException
     */
    public Credential putCredential(final String userName, final String passwordHash) throws EPFException {
    	final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put("javax.persistence.jdbc.user", userName);
        props.put("javax.persistence.jdbc.password", passwordHash);
        final Var<Exception> error = new Var<>();
        credentials.computeIfAbsent(userName, name -> {
            return newCredential(props, error);
        });
        if(error.get() != null){
        	throw new EPFException(error.get());
        }
        final Var<Credential> cred = new Var<>();
        credentials.computeIfPresent(userName, (name, credential) -> {
            if(credential == null){
                credential = newCredential(props, error);
            }
            else{
                synchronized(credential){
                    if(!props.equals(credential.getFactory().getProperties())){
                    	credential.close();
                        credential = newCredential(props, error);
                    }
                }
            }
            cred.set(credential);
            return credential;
        });
        if(error.get() != null){
        	throw new EPFException(error.get());
        }
        return cred.get();
    }
    
    /**
     * @param props
     * @param error
     * @return
     */
    protected Credential newCredential(final Map<String, Object> props, final Var<Exception> error){
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        Credential result = null;
        try{
            factory = Persistence.createEntityManagerFactory(name, props);
            manager = factory.createEntityManager();
            result = new Credential(factory, manager);
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
        return result;
    }
    
    /**
     * @param userName
     * @return
     */
    public Credential getCredential(final String userName){
        return credentials.get(userName);
    }
    
    /**
     * @param userName
     * @return
     */
    public Credential removeCredential(final String userName){
        return credentials.remove(userName);
    }

    /**
     *
     */
    public void close() {
    	credentials.forEach((name, credential) -> {
    		credential.close();
		});
        credentials.clear();
    }
}
