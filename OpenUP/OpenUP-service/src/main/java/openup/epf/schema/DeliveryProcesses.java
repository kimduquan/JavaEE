/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.epf.schema;

import java.util.List;
import epf.schema.delivery_processes.DeliveryProcess;
import java.security.Principal;
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
public class DeliveryProcesses implements openup.share.epf.schema.DeliveryProcesses {
    
    @Inject
    private Cache cache;
    
    @Inject
    private Principal principal;
    
    @Name("Contents")
    @Override
    public List<DeliveryProcess> getDeliveryProcesses() throws Exception{
        return cache.getNamedQueryResult(
                principal,
                DeliveryProcess.DELIVERY_PROCESSES, 
                DeliveryProcess.class);
    }
}
