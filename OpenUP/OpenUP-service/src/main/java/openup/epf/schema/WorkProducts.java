/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.epf.schema;

import epf.schema.work_products.Domain;
import java.security.Principal;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import openup.persistence.Cache;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;

/**
 *
 * @author FOXCONN
 */
@Type
@RequestScoped
public class WorkProducts implements openup.api.epf.schema.WorkProducts {
    
    @Inject
    private Cache cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
    @Override
    public List<Domain> getDomains() throws Exception{
        return cache.getNamedQueryResult(
                principal,
                Domain.DOMAINS, 
                Domain.class);
    }
}
