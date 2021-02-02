/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.batch;

import epf.schema.openup.Role;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

/**
 *
 * @author FOXCONN
 */
@Path("batch/event")
@RolesAllowed(Role.ANY_ROLE)
@SessionScoped
public class Events implements openup.client.batch.Events, Serializable {
    
    private Map<Long, SseBroadcaster> broadcasters;
    
    @Context
    private Sse serverSent;
    
    @Context 
    private SseEventSink sink;
    
    @PostConstruct
    void postConstruct(){
        broadcasters = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
        broadcasters.forEach((id, b) -> { 
            b.close(); 
        });
        broadcasters.clear();
    }

    @Override
    public void register(long executionId) {
        broadcasters.computeIfAbsent(
                executionId, 
                id -> { 
                    return serverSent.newBroadcaster(); 
                }
        ).register(sink);
    }
}
