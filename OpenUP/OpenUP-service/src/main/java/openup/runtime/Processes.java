/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.runtime;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.ws.rs.Path;
import openup.schema.Role;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import openup.client.runtime.ProcessInfo;
import org.eclipse.microprofile.context.ManagedExecutor;

/**
 *
 * @author FOXCONN
 */
@Path("runtime/process")
@RolesAllowed(Role.ANY_ROLE)
@SessionScoped
public class Processes implements openup.client.runtime.Processes, Serializable {
    
    private Map<Long, ProcessTask> processes;
    
    @Inject
    private ManagedExecutor executor;
    
    @PostConstruct
    void postConstruct(){
        processes = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
        processes.forEach((pid, process) -> {
            try {
                process.close();
            } 
            catch (Exception ex) {
            }
        });
        processes.clear();
    }

    @Override
    public long start(List<String> command, String directory) throws Exception {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        builder.directory(new File(directory));
        Process process = builder.start();
        Long pid = process.pid();
        processes.put(pid, new ProcessTask(process));
        return pid;
    }

    @Override
    public void stop() throws Exception {
        processes.forEach((pid, process) -> {
            try(ProcessTask task = process){
                task.close();
            } 
            catch (Exception ex) {
            }
        });
        processes.clear();
    }

    @Override
    public ProcessInfo info(long pid) {
        ProcessTask process = processes.get(pid);
        if(process != null){
            return process.getInfo();
        }
        throw new NotFoundException();
    }

    @Override
    public int destroy(long pid) throws Exception {
        ProcessTask process = processes.remove(pid);
        if(process == null){
            throw new NotFoundException();
        }
        process.close();
        return process.getProcess().exitValue();
    }

    @Override
    public List<ProcessInfo> getProcesses(boolean isAlive) {
        return processes.values()
                .stream()
                .filter(process -> process.getProcess().isAlive() == isAlive)
                .map(process -> {
                    return process.getInfo();
                })
                .collect(Collectors.toList());
    }

    @Override
    public void output(long pid, Sse sse, SseEventSink sink) {
        ProcessTask p = processes.computeIfPresent(pid, (id, process) -> {
            if(process.getBroadcaster() == null){
                process.setBroadcaster(sse.newBroadcaster());
                process.setBuilder(sse.newEventBuilder());
                executor.submit(process);
            }
            process.getBroadcaster().register(sink);
            return process;
        });
        if(p == null){
            throw new NotFoundException();
        }
    }
    
}
