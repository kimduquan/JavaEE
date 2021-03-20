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
	
	/**
	 * 
	 */
	String PROCESS = "process";
    
	/**
	 * @param command
	 * @param directory
	 * @return
	 */
	@Path(PROCESS)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    long start(@MatrixParam("command") final List<String> command, @MatrixParam("directory") final String directory);
    
    /**
     * @param client
     * @param command
     * @param directory
     * @return
     */
    static long start(final Client client, final String directory, final String... command){
    	return client.request(
    			target -> target.path(PROCESS).matrixParam("command", (Object[])command).matrixParam("directory", directory), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(null, Long.class);
    }
    
    /**
     * @param isAlive
     * @return
     */
    @Path("process")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<ProcessInfo> getProcesses(@QueryParam("isAlive") final boolean isAlive);
    
    /**
     * @param client
     * @param isAlive
     * @return
     */
    static List<ProcessInfo> getProcesses(final Client client, final boolean isAlive){
    	return client.request(
    			target -> target.path(PROCESS).queryParam("isAlive", isAlive), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<List<ProcessInfo>>() {});
    }
    
    /**
     * 
     */
    @Path(PROCESS)
    @DELETE
    void stop();
    
    /**
     * @param client
     */
    static void stop(final Client client) {
    	client.request(
    			target -> target.path(PROCESS), 
    			req -> req
    			)
    	.delete();
    }
    
    /**
     * @param pid
     * @return
     */
    @GET
    @Path(PROCESS)
    @Produces(MediaType.APPLICATION_JSON)
    ProcessInfo info(
    		@QueryParam("pid")
    		@NotBlank
    		@Positive
    		final long pid
    		);
    
    /**
     * @param client
     * @param pid
     * @return
     */
    static ProcessInfo info(final Client client, long pid) {
    	return client.request(
    			target -> target.path(String.valueOf(pid)), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(ProcessInfo.class);
    }
    
    /**
     * @param pid
     * @return
     */
    @DELETE
    @Path(PROCESS)
    @Produces(MediaType.APPLICATION_JSON)
    int destroy(
    		@QueryParam("pid")
    		@NotBlank
    		@Positive
    		final long pid
    		);
    
    /**
     * @param client
     * @param pid
     * @return
     */
    static int destroy(final Client client, final long pid) {
    	return client.request(
    			target -> target.path(String.valueOf(pid)), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.delete(Integer.class);
    }
    
    /**
     * @param pid
     * @param sse
     * @param sink
     */
    @GET
    @Path("process/out")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    void output(
    		@QueryParam("pid")
    		@NotBlank
    		@Positive
    		final long pid, 
    		@Context 
    		final Sse sse, 
    		@Context 
    		final SseEventSink sink
    		);
}
