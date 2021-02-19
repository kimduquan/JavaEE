/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.batch.listener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.sse.Sse;
import openup.client.batch.JobInstance;
import openup.client.batch.StepExecution;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    private Map<Long, Broadcaster<JobInstance>> jobs;
    private Map<Long, Broadcaster<StepExecution>> steps;
    
    @PostConstruct
    void postConstruct(){
        jobs = new ConcurrentHashMap<>();
        steps = new ConcurrentHashMap<>();
    }
    
    @PreDestroy
    void preDestroy(){
        jobs.forEach((id, broadcaster) -> {
            broadcaster.close();
        });
        jobs.clear();
        
        steps.forEach((id, broadcaster) -> {
            broadcaster.close();
        });
        steps.clear();
    }
    
    public Broadcaster<JobInstance> registerJob(long instanceId, Sse sse){
        return jobs.computeIfAbsent(instanceId, id -> {
            return new Broadcaster<>(sse.newBroadcaster(), sse.newEventBuilder());
        });
    }
    
    public Broadcaster<JobInstance> getJob(long instanceId){
        return jobs.get(instanceId);
    }
    
    public Broadcaster<StepExecution> registerStep(long stepExecutionId, Sse sse){
        return steps.computeIfAbsent(stepExecutionId, id -> {
            return new Broadcaster<>(sse.newBroadcaster(), sse.newEventBuilder());
        });
    }
    
    public Broadcaster<StepExecution> getStep(long stepExecutionId){
        return steps.get(stepExecutionId);
    }
}
