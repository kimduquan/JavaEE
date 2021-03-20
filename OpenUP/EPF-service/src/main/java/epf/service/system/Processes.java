/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.system;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.Path;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.context.ManagedExecutor;

import epf.client.EPFException;
import epf.client.system.ProcessInfo;
import epf.schema.roles.Role;
import epf.util.Var;
import epf.util.logging.Log;

/**
 *
 * @author FOXCONN
 */
@Path("system")
@RolesAllowed(Role.DEFAULT_ROLE)
@SessionScoped
public class Processes implements epf.client.system.Processes, Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient final Map<Long, ProcessTask> processes;
    /**
     * 
     */
	@Inject
    private transient Logger logger;
    
    /**
     * 
     */
    @Inject
    private transient ManagedExecutor executor;
    
    /**
     * 
     */
    public Processes() {
    	processes = new ConcurrentHashMap<>();
    }
    
    /**
     * 
     */
    @PreDestroy
    protected void preDestroy(){
    	processes.forEach((pid, process) -> {
            try {
                process.close();
            } 
            catch (Exception e) {
            	logger.log(Level.WARNING, e.getMessage(), e);
            }
        });
        processes.clear();
    }

    @Override
    @Log
    public long start(final List<String> command, final String directory) {
    	final ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.directory(new File(directory));
        Process process;
		try {
			process = builder.start();
		} 
		catch (IOException e) {
			throw new EPFException(e);
		}
        final Long pid = process.pid();
        processes.put(pid, new ProcessTask(process));
        return pid;
    }

    @Override
    @Log
    public void stop() {
    	final Var<Exception> error = new Var<>();
    	processes.forEach((pid, process) -> {
            try{
            	process.close();
            } 
            catch (Exception e) {
            	logger.log(Level.SEVERE, e.getMessage(), e);
            	error.set(e);
            }
        });
        processes.clear();
        if(error.get() != null) {
        	throw new EPFException(error.get());
        }
    }

    @Override
    public ProcessInfo info(final long pid) {
    	final ProcessTask process = processes.get(pid);
        if(process != null){
            return process.getInfo();
        }
        throw new NotFoundException();
    }

    @Override
    public int destroy(final long pid) {
    	final ProcessTask process = processes.remove(pid);
        if(process == null){
            throw new NotFoundException();
        }
        process.close();
        return process.getProcess().exitValue();
    }

    @Override
    public List<ProcessInfo> getProcesses(final boolean isAlive) {
        return processes.values()
                .stream()
                .filter(process -> process.getProcess().isAlive() == isAlive)
                .map(process -> {
                    return process.getInfo();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void output(final long pid, final Sse sse, final SseEventSink sink) {
    	final ProcessTask procTask = processes.computeIfPresent(pid, (id, process) -> {
            if(process.getBroadcaster() == null){
                process.setBroadcaster(sse.newBroadcaster());
                process.setBuilder(sse.newEventBuilder());
                executor.submit(process);
            }
            process.getBroadcaster().register(sink);
            return process;
        });
        if(procTask == null){
            throw new NotFoundException();
        }
    }
    
}
