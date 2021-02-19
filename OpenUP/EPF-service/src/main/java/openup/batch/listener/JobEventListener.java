/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.batch.listener;

import javax.batch.api.listener.JobListener;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import openup.client.batch.JobInstance;

/**
 *
 * @author FOXCONN
 */
@Dependent
@Named("JobListener")
public class JobEventListener implements JobListener {
    
    public JobEventListener(){}
    
    @Inject
    private Application broadcaster;
    
    @Inject
    private JobContext context;

    @Override
    public void beforeJob() throws Exception {
        Broadcaster<JobInstance> job = broadcaster.getJob(context.getInstanceId());
        if(job != null) { 
            job.broadcast(build());
        }
    }

    @Override
    public void afterJob() throws Exception {
        Broadcaster<JobInstance> job = broadcaster.getJob(context.getInstanceId());
        if(job != null) { 
            job.broadcast(build());
        }
    }
    
    JobInstance build(){
        JobInstance instance = new JobInstance();
        instance.setInstanceId(context.getInstanceId());
        instance.setJobName(context.getJobName());
        return instance;
    }
}
