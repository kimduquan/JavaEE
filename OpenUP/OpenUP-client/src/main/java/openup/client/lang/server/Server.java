/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.server;

import openup.client.lang.InitializeError;
import openup.client.lang.InitializeResult;
import openup.client.lang.params.InitializeParams;
import openup.client.lang.params.InitializedParams;
import openup.client.lang.params.LogTraceParams;
import openup.client.lang.params.SetTraceParams;

/**
 *
 * @author FOXCONN
 */
public interface Server extends openup.client.rpc.Server {
    InitializeResult initialize(InitializeParams params) throws InitializeError;
    void initialized(InitializedParams params);
    Object shutdown() throws Error;
    void exit();
    void logTrace(LogTraceParams params);
    void setTrace(SetTraceParams params);
}
