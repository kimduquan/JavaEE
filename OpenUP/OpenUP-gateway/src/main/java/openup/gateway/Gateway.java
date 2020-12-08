/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.context.ThreadContext;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Gateway extends Application {
    
    public static void shutdownExecutor(@Disposes ManagedExecutor executor){
        executor.shutdownNow();
    }
    
    @javax.enterprise.inject.Produces
    @ApplicationScoped
    public static ManagedExecutor getExecutor(){
        return ManagedExecutor
                .builder()
                .propagated(ThreadContext.APPLICATION)
                .build();
    }
}
