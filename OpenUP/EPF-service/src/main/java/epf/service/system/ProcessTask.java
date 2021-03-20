/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.sse.OutboundSseEvent.Builder;
import epf.client.system.ProcessInfo;
import javax.ws.rs.sse.SseBroadcaster;

/**
 *
 * @author FOXCONN
 */
public class ProcessTask implements Runnable {
	
	/**
	 * 
	 */
	private static final Logger logger = Logger.getLogger(Processes.class.getName());
    
    /**
     * 
     */
    private SseBroadcaster broadcaster;
    /**
     * 
     */
    private transient final Process process;
    /**
     * 
     */
    private Builder builder;

    /**
     * @param process
     */
    public ProcessTask(final Process process) {
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
        catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * 
     */
    public void close() {
        broadcaster.close();
        process.destroyForcibly();
    }

    public Process getProcess() {
        return process;
    }

    public void setBroadcaster(final SseBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }
    
    /**
     * @return
     */
    public ProcessInfo getInfo(){
    	final ProcessInfo info = new ProcessInfo();
    	final ProcessHandle.Info procInfo = process.info();
    	procInfo.arguments().ifPresent(info::setArguments);
    	procInfo.command().ifPresent(info::setCommand);
    	procInfo.commandLine().ifPresent(info::setCommandLine);
    	procInfo.startInstant().ifPresent(info::setStart);
    	procInfo.totalCpuDuration().ifPresent(info::setTotalCpu);
    	procInfo.user().ifPresent(info::setUser);
        return info;
    }

    public SseBroadcaster getBroadcaster() {
        return broadcaster;
    }

    public Builder getBuilder() {
        return builder;
    }

    public void setBuilder(final Builder builder) {
        this.builder = builder;
    }
}
