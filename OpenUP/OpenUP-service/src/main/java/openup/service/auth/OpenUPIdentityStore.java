/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.auth;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class OpenUPIdentityStore implements IdentityStore {
    
    @PersistenceUnit
    private EntityManagerFactory factory;
    
    private Map<String, EntityManager> managers;
    
    @PreDestroy
    void preDestroy(){
        managers.values()
            .stream()
            .parallel()
            .forEach(EntityManager::close);
        managers.clear();
        if(factory.isOpen()){
            factory.close();
        }
    }
    
    @PostConstruct
    void postConstruct(){
        managers = new HashMap<>();
    }
    
    @Produces @SessionScoped
    public EntityManager getEntityManager(Principal principal){
        return managers.get(principal.getName());
    }
    
    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", credential.getCaller());
        props.put("javax.persistence.jdbc.password", credential.getPasswordAsString());
        if(factory.isOpen()){
            EntityManager manager = factory.createEntityManager(props);
            if(manager.isOpen()){
                EntityTransaction transaction = manager.getTransaction();
                transaction.begin();
                if(transaction.isActive()){
                    transaction.commit();
                    manager = managers.putIfAbsent(credential.getCaller(), manager);
                    if(manager != null){
                        if(manager.isOpen()){
                            manager.close();
                        }
                    }
                    return new CredentialValidationResult(credential.getCaller());
                }
                manager.close();
            }
            factory.close();
        }
        return INVALID_RESULT;
    }
}
