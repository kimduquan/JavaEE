/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.web.auth;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class DatabaseIdentityStore implements IdentityStore {
    
    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        Map<String, String> props = new HashMap<>();
        props.put("javax.persistence.jdbc.user", credential.getCaller());
        props.put("javax.persistence.jdbc.password", credential.getPasswordAsString());
        boolean isSuccess = false;
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("OpenUP", props);
        if(factory.isOpen()){
            EntityManager manager = factory.createEntityManager();
            if(manager.isOpen()){
                EntityTransaction transaction = manager.getTransaction();
                transaction.begin();
                if(transaction.isActive()){
                    transaction.commit();
                    isSuccess = true;
                }
                manager.close();
            }
            factory.close();
        }
        if(isSuccess){
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }
}
