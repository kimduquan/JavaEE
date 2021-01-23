/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.server;

/**
 *
 * @author FOXCONN
 */
public interface Telemetry extends openup.client.rpc.Server {
    void event(Object params);
}
