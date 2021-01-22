/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch.api;

/**
 *
 * @author FOXCONN
 */
public enum InstanceState {
    SUBMITTED, 
    JMS_QUEUED, 
    JMS_CONSUMED, 
    DISPATCHED, 
    FAILED, 
    STOPPED, 
    COMPLETED, 
    ABANDONED
}
