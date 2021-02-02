/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.event;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

/**
 *
 * @author FOXCONN
 * @param <T>
 */
public class Broadcaster<T> {
    
    private SseBroadcaster broadcaster;
    private OutboundSseEvent.Builder builder;

    public Broadcaster(SseBroadcaster broadcaster, OutboundSseEvent.Builder builder) {
        this.broadcaster = broadcaster;
        this.builder = builder;
    }
    
    public void broadcast(T data){
        OutboundSseEvent event = builder
                .data(data)
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .build();
        broadcaster.broadcast(event);
    }
    
    public void close(){
        broadcaster.close();
    }
    
    public void register(SseEventSink sink){
        broadcaster.register(sink);
    }
}
