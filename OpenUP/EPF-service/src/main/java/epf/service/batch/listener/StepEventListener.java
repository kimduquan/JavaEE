/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.batch.listener;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.batch.api.listener.StepListener;
import javax.batch.runtime.context.StepContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import openup.client.batch.BatchStatus;
import openup.client.batch.Metric;
import openup.client.batch.MetricType;
import openup.client.batch.StepExecution;

/**
 *
 * @author FOXCONN
 */
@Dependent
@Named("StepListener")
public class StepEventListener implements StepListener {
    
    @Inject
    private Application broadcaster;
    
    @Inject
    private StepContext context;
    
    public StepEventListener(){}

    @Override
    public void beforeStep() throws Exception {
        Broadcaster<StepExecution> step = broadcaster.getStep(context.getStepExecutionId());
        if(step != null){
            step.broadcast(build());
        }
    }

    @Override
    public void afterStep() throws Exception {
        Broadcaster<StepExecution> step = broadcaster.getStep(context.getStepExecutionId());
        if(step != null){
            step.broadcast(build());
        }
    }
    
    StepExecution build(){
        StepExecution step = new StepExecution();
        step.setBatchStatus(BatchStatus.valueOf(context.getBatchStatus().name()));
        step.setExitStatus(context.getExitStatus());
        step.setMetrics(
                Stream.of(context.getMetrics())
                        .map(StepEventListener::map)
                        .collect(Collectors.toList())
        );
        step.setStepExecutionId(context.getStepExecutionId());
        step.setStepName(context.getStepName());
        return step;
    }
    
    static Metric map(javax.batch.runtime.Metric m){
        Metric metric = new Metric();
        metric.setType(MetricType.valueOf(m.getType().name()));
        metric.setValue(m.getValue());
        return metric;
    }
}
