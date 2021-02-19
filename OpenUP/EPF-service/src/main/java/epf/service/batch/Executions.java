/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.schema.roles.Role;
import openup.client.batch.BatchStatus;
import openup.client.batch.JobExecution;
import openup.client.batch.Metric;
import openup.client.batch.MetricType;
import openup.client.batch.StepExecution;

/**
 *
 * @author FOXCONN
 */
@Path("batch/excution")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class Executions implements openup.client.batch.Executions {
    
    private JobOperator operator;
    
    @PostConstruct
    void postConstruct(){
        operator = BatchRuntime.getJobOperator();
    }

    @Override
    public void abandon(long executionId) {
        operator.abandon(executionId);
    }

    @Override
    public JobExecution getJobExecution(long executionId) {
        JobExecution execution = new JobExecution();
        javax.batch.runtime.JobExecution e = operator.getJobExecution(executionId);
        execution.setBatchStatus(BatchStatus.valueOf(e.getBatchStatus().name()));
        execution.setCreateTime(e.getCreateTime());
        execution.setEndTime(e.getEndTime());
        execution.setExecutionId(e.getExecutionId());
        execution.setExitStatus(e.getExitStatus());
        execution.setJobName(e.getJobName());
        execution.setJobParameters(e.getJobParameters());
        execution.setLastUpdatedTime(e.getLastUpdatedTime());
        execution.setStartTime(e.getStartTime());
        return execution;
    }

    @Override
    public long restart(long executionId, Properties props) {
        return operator.restart(executionId, props);
    }

    @Override
    public void stop(long executionId) {
        operator.stop(executionId);
    }

    @Override
    public List<StepExecution> getStepExecutions(long jobExecutionId) {
        List<StepExecution> steps = new ArrayList<>();
        operator.getStepExecutions(jobExecutionId).forEach(execution -> {
            StepExecution step = new StepExecution();
            step.setBatchStatus(BatchStatus.valueOf(execution.getBatchStatus().name()));
            step.setEndTime(execution.getEndTime());
            step.setExitStatus(execution.getExitStatus());
            List<Metric> metrics = new ArrayList<>();
            for(javax.batch.runtime.Metric m : execution.getMetrics()){
                Metric metric = new Metric();
                metric.setType(MetricType.valueOf(m.getType().name()));
                metric.setValue(m.getValue());
                metrics.add(metric);
            }
            step.setMetrics(metrics);
            step.setPersistentUserData(execution.getPersistentUserData());
            step.setStartTime(execution.getStartTime());
            step.setStepExecutionId(execution.getStepExecutionId());
            step.setStepName(execution.getStepName());
            steps.add(step);
        });
        return steps;
    }
}
