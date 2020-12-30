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
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author FOXCONN
 */
public class Context implements AutoCloseable {
    
    private Map<String, Credential> credentials;
    
    public Context(){
        credentials = new ConcurrentHashMap<>();
    }
    
    public Credential putCredential(String userName, EntityManagerFactory factory, EntityManager manager){
        credentials.computeIfPresent(userName, (key, credential) -> {
            if(credential != null){
                try {
                    credential.close();
                } 
                catch (Exception ex) {
                    Logger.getLogger(Context.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return new Credential(factory, manager);
        });
        return credentials.computeIfAbsent(userName, key -> {
            return new Credential(factory, manager);
        });
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
