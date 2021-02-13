/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.runtime;

import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

/**
 *
 * @author FOXCONN
 */
@Path("runtime/process")
public interface Processes {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    long start(@MatrixParam("command") List<String> command, @MatrixParam("directory") String directory) throws Exception;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProcessInfo> getProcesses(@QueryParam("isAlive") boolean isAlive);
    
    @DELETE
    void stop() throws Exception;
    
    @GET
    @Path("{pid}")
    @Produces(MediaType.APPLICATION_JSON)
    ProcessInfo info(@PathParam("pid") long pid);
    
    @DELETE
    @Path("{pid}")
    @Produces(MediaType.APPLICATION_JSON)
    int destroy(@PathParam("pid") long pid) throws Exception;
    
    @GET
    @Path("{pid}/out")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    void output(@PathParam("pid") long pid, @Context Sse sse, @Context SseEventSink sink);
}
