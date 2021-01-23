/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.server;

import openup.client.lang.params.RegistrationParams;
import openup.client.lang.params.UnregistrationParams;

/**
 *
 * @author FOXCONN
 */
public interface Client extends openup.client.rpc.Server {
    Object registerCapability(RegistrationParams params) throws Error;
    Object unregisterCapability(UnregistrationParams params) throws Error;
}
