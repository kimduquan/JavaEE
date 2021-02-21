/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.system;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import epf.util.client.Client;

/**
 *
 * @author FOXCONN
 */
@Path("system")
public interface Processes {
    
	@Path("process")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    long start(@MatrixParam("command") List<String> command, @MatrixParam("directory") String directory) throws Exception;
    
    static long start(Client client, List<String> command, String directory) throws Exception{
    	Object[] cmd = command.toArray();
    	return client.request(
    			target -> target.path("process").matrixParam("command", (Object[])cmd).matrixParam("directory", directory), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(null, Long.class);
    }
    
    @Path("process")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProcessInfo> getProcesses(@QueryParam("isAlive") boolean isAlive);
    
    static List<ProcessInfo> getProcesses(Client client, boolean isAlive){
    	return client.request(
    			target -> target.path("process").queryParam("isAlive", isAlive), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<List<ProcessInfo>>() {});
    }
    
    @Path("process")
    @DELETE
    void stop() throws Exception;
    
    static void stop(Client client) throws Exception{
    	client.request(
    			target -> target.path("process"), 
    			req -> req
    			)
    	.delete();
    }
    
    @GET
    @Path("process")
    @Produces(MediaType.APPLICATION_JSON)
    ProcessInfo info(
    		@QueryParam("pid")
    		@NotBlank
    		@Positive
    		long pid
    		);
    
    static ProcessInfo info(Client client, long pid) {
    	return client.request(
    			target -> target.path(String.valueOf(pid)), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(ProcessInfo.class);
    }
    
    @DELETE
    @Path("process")
    @Produces(MediaType.APPLICATION_JSON)
    int destroy(
    		@QueryParam("pid")
    		@NotBlank
    		@Positive
    		long pid
    		) throws Exception;
    
    static int destroy(Client client, long pid) throws Exception{
    	return client.request(
    			target -> target.path(String.valueOf(pid)), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.delete(Integer.class);
    }
    
    @GET
    @Path("process/out")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    void output(
    		@QueryParam("pid")
    		@NotBlank
    		@Positive
    		long pid, 
    		@Context 
    		Sse sse, 
    		@Context 
    		SseEventSink sink
    		);
}
