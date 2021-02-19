/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.schema.roles.Role;
import openup.client.batch.JobExecution;
import openup.client.batch.JobInstance;

/**
 *
 * @author FOXCONN
 */
@Path("batch/job")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class Jobs implements openup.client.batch.Jobs {
    
    private JobOperator operator;
    
    @PostConstruct
    void postConstruct(){
        operator = BatchRuntime.getJobOperator();
    }

    @Override
    public Set<String> getJobNames() {
        return operator.getJobNames();
    }

    @Override
    public List<JobInstance> getJobInstances(String jobName, int start, int count) {
        List<JobInstance> jobs = new ArrayList<>();
        operator.getJobInstances(jobName, start, start).forEach(job -> {
            JobInstance j = new JobInstance();
            j.setInstanceId(job.getInstanceId());
            j.setJobName(job.getJobName());
            jobs.add(j);
        });
        return jobs;
    }

    @Override
    public List<JobExecution> getJobExecutions(String jobName, long instanceId) {
        List<JobExecution> executions = new ArrayList<>();
        return executions;
    }

    @Override
    public long start(String jobXMLName, Properties jobParameters) {
        return operator.start(jobXMLName, jobParameters);
    }
    
}
