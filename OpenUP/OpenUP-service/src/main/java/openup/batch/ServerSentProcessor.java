/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.batch;

import javax.annotation.PostConstruct;
import javax.batch.api.chunk.ItemProcessor;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.SseEventSink;

/**
 *
 * @author FOXCONN
 */
@Dependent
@Named("ServerSentProcessor")
public class ServerSentProcessor implements ItemProcessor {
    
    private OutboundSseEvent.Builder builder;
    private SseEventSink sink;
    
    @PostConstruct
    void postConstruct(){
        
    }

    @Override
    public Object processItem(Object item) throws Exception {
        builder.data(item);
        builder.mediaType(MediaType.APPLICATION_JSON_TYPE);
        builder.build();
        sink.send(builder.build());
        return item;
    }
    
}
