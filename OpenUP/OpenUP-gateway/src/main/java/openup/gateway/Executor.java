/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Executor {
    
    @Produces
    @ApplicationScoped
    public static ManagedExecutor getExecutor(){
        return ManagedExecutor
                .builder()
                .propagated(ThreadContext.APPLICATION)
                .build();
    }
}
