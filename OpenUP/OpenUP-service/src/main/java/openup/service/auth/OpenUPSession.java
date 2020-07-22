/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.auth;

import java.io.Serializable;
import java.security.Principal;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class OpenUPSession implements Serializable {
    
    @Inject
    private EntityManager entityManager;
    
    public EntityManager getEntityManager(){
        return entityManager;
    }
}
