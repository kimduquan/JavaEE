/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
        return credentials.computeIfAbsent(userName, key -> {
            return new Credential(factory, manager);
        });
    }
    
    public Credential getCredential(String userName){
        return credentials.get(userName);
    }

    @Override
    public void close() throws Exception {
        for(Credential credential : credentials.values()){
            credential.close();
        }
        credentials.clear();
    }
}
