/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.batch;

/**
 *
 * @author FOXCONN
 */
public enum BatchStatus {
    STARTING, 
    STARTED, 
    STOPPING, 
    STOPPED, 
    FAILED, 
    COMPLETED, 
    ABANDONED
}
