/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch.api;

import java.util.Map;

/**
 *
 * @author FOXCONN
 */
public class PartitionedStep {
    private long partitionNumber;
    private String batchStatus;
    private String startTime;
    private String endTime;
    private String exitStatus;
    private Map<String, Object> metrics;

    public long getPartitionNumber() {
        return partitionNumber;
    }

    public void setPartitionNumber(long partitionNumber) {
        this.partitionNumber = partitionNumber;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(String exitStatus) {
        this.exitStatus = exitStatus;
    }

    public Map<String, Object> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Object> metrics) {
        this.metrics = metrics;
    }
}
