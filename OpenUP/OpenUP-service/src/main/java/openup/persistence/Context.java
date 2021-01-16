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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author FOXCONN
 */
public class Context implements AutoCloseable {
    
    private Map<String, Credential> credentials;
    
    public Context(){
        credentials = new ConcurrentHashMap<>();
    }
    
    public Credential putCredential(String userName, String unit, String password) throws Exception{
        Map<String, Object> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", userName);
        props.put("javax.persistence.jdbc.password", password);
        List<Exception> errors = new ArrayList<>();
        credentials.computeIfAbsent(userName, name -> {
            return newCredential(unit, props, errors);
        });
        if(!errors.isEmpty()){
            throw errors.get(0);
        }
        Credential cred = credentials.computeIfPresent(userName, (name, credential) -> {
            if(credential != null){
                synchronized(credential){
                    if(!props.equals(credential.getFactory().getProperties())){
                        try {
                            credential.close();
                        } 
                        catch (Exception ex) {
                            Logger.getLogger(Context.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        credential = newCredential(unit, props, errors);
                    }
                }
            }
            else{
                credential = newCredential(unit, props, errors);
            }
            return credential;
        });
        if(!errors.isEmpty()){
            throw errors.get(0);
        }
        return cred;
    }
    
    Credential newCredential(String unit, Map<String, Object> props, List<Exception> errors){
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try{
            factory = Persistence.createEntityManagerFactory(unit, props);
            manager = factory.createEntityManager();
            return new Credential(factory, manager);
        }
        catch (Exception ex) {
            errors.add(ex);
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
        for(Credential credential : credentials.values()){
            credential.close();
        }
        credentials.clear();
    }
}
