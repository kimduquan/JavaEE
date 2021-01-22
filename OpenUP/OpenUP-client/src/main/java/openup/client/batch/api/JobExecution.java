/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch.api;

import java.util.List;
import java.util.Map;

/**
 *
 * @author FOXCONN
 */
public class JobExecution {
    private String jobName;
    private long executionId;
    private long instanceId;
    private String batchStatus;
    private String exitStatus;
    private String createTime;
    private String endTime;
    private String lastUpdatedTime;
    private String startTime;
    private Map<String, Object> jobParameters;
    private String restUrl;
    private String serverId;
    private String logpath;
    private List<StepExecution> stepExecutions;
    private List<Link> _links;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(long executionId) {
        this.executionId = executionId;
    }

    public long getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(long instanceId) {
        this.instanceId = instanceId;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(String exitStatus) {
        this.exitStatus = exitStatus;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(String lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Map<String, Object> getJobParameters() {
        return jobParameters;
    }

    public void setJobParameters(Map<String, Object> jobParameters) {
        this.jobParameters = jobParameters;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public List<StepExecution> getStepExecutions() {
        return stepExecutions;
    }

    public void setStepExecutions(List<StepExecution> stepExecutions) {
        this.stepExecutions = stepExecutions;
    }

    public List<Link> getLinks() {
        return _links;
    }

    public void setLinks(List<Link> _links) {
        this._links = _links;
    }
}
