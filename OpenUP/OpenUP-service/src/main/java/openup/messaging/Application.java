/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.messaging;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;
import javax.jms.Topic;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
public class Application {
    
    @Resource(lookup = "batch/jobs/instance/submitted")
    private Topic instance_submitted;
    @Resource(lookup = "batch/jobs/instance/jms_queued")
    private Topic instance_queued;
    @Resource(lookup = "batch/jobs/instance/jms_consumed")
    private Topic instance_consumed;
    @Resource(lookup = "batch/jobs/instance/dispatched")
    private Topic instance_dispatched;
    @Resource(lookup = "batch/jobs/instance/completed")
    private Topic instance_completed;
    @Resource(lookup = "batch/jobs/instance/stopped")
    private Topic instance_stopped;
    @Resource(lookup = "batch/jobs/instance/stopping")
    private Topic instance_stopping;
    @Resource(lookup = "batch/jobs/instance/failed")
    private Topic instance_failed;
    @Resource(lookup = "batch/jobs/instance/purged")
    private Topic instance_purged;
    
    @Resource(lookup = "batch/jobs/execution/restarting")
    private Topic execution_restarting;
    @Resource(lookup = "batch/jobs/execution/starting")
    private Topic execution_starting;
    @Resource(lookup = "batch/jobs/execution/completed")
    private Topic execution_completed;
    @Resource(lookup = "batch/jobs/execution/failed")
    private Topic execution_failed;
    @Resource(lookup = "batch/jobs/execution/stopped")
    private Topic execution_stopped;
    @Resource(lookup = "batch/jobs/execution/jobLogPart")
    private Topic execution_jobLogPart;
    
    @Resource(lookup = "batch/jobs/execution/step/started")
    private Topic step_started;
    @Resource(lookup = "batch/jobs/execution/step/completed")
    private Topic step_completed;
    @Resource(lookup = "batch/jobs/execution/step/failed")
    private Topic step_failed;
    @Resource(lookup = "batch/jobs/execution/step/stopped")
    private Topic step_stopped;
    @Resource(lookup = "batch/jobs/execution/step/checkpoint")
    private Topic step_checkpoint;
    
    @Resource(lookup = "batch/jobs/execution/partition/started")
    private Topic partition_started;
    @Resource(lookup = "batch/jobs/execution/partition/completed")
    private Topic partition_completed;
    @Resource(lookup = "batch/jobs/execution/partition/failed")
    private Topic partition_failed;
    @Resource(lookup = "batch/jobs/execution/partition/stopped")
    private Topic partition_stopped;
    
    @Resource(lookup = "batch/jobs/execution/split-flow/started")
    private Topic flow_started;
    @Resource(lookup = "batch/jobs/execution/split-flow/ended")
    private Topic flow_ended;
    
    @Resource(lookup = "jms/batch/connectionFactory")
    private ConnectionFactory connectionFactory;
    
    @PostConstruct
    void postConstruct(){
        
    }
}
