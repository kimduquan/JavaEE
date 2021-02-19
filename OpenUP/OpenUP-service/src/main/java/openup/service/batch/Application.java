/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.batch;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseEventSink;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    private Map<Long, OutboundSseEvent.Builder> builders;
    private Map<Long, SseEventSink> sinks;
    
    @PostConstruct
    void postConstruct(){
        builders = new ConcurrentHashMap<>();
        sinks = new ConcurrentHashMap<>();
    }
    
    public void putSSEEvent(long executionId, OutboundSseEvent.Builder builder, SseEventSink sink){
        builders.put(executionId, builder);
        sinks.put(executionId, sink);
    }
    
    public OutboundSseEvent.Builder removeBuilder(long executionId){
        return builders.remove(executionId);
    }
    
    public SseEventSink removeSink(long executionId){
        return sinks.remove(executionId);
    }
}
