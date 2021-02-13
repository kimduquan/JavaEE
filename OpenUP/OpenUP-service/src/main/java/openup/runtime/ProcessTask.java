/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.runtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.sse.OutboundSseEvent.Builder;
import epf.client.runtime.ProcessInfo;
import javax.ws.rs.sse.SseBroadcaster;

/**
 *
 * @author FOXCONN
 */
public class ProcessTask implements Runnable, AutoCloseable {
    
    private SseBroadcaster broadcaster;
    private Process process;
    private Builder builder;

    public ProcessTask(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        try(InputStreamReader input = new InputStreamReader(process.getInputStream())){
            try(BufferedReader reader = new BufferedReader(input)){
                reader.lines().forEach(line -> {
                    broadcaster.broadcast(builder.data(line).build());
                });
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(ProcessTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void close() throws Exception {
        broadcaster.close();
        process.destroyForcibly();
    }

    public Process getProcess() {
        return process;
    }

    public void setBroadcaster(SseBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }
    
    public ProcessInfo getInfo(){
        ProcessInfo info = new ProcessInfo();
        ProcessHandle.Info i = process.info();
        i.arguments().ifPresent(info::setArguments);
        i.command().ifPresent(info::setCommand);
        i.commandLine().ifPresent(info::setCommandLine);
        i.startInstant().ifPresent(info::setStart);
        i.totalCpuDuration().ifPresent(info::setTotalCpu);
        i.user().ifPresent(info::setUser);
        return info;
    }

    public SseBroadcaster getBroadcaster() {
        return broadcaster;
    }

    public Builder getBuilder() {
        return builder;
    }

    public void setBuilder(Builder builder) {
        this.builder = builder;
    }
}
