/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import epf.client.system.ProcessInfo;

/**
 *
 * @author FOXCONN
 */
public class ProcessTask implements Runnable {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(Processes.class.getName());
    
    /**
     * 
     */
    private transient final Process process;
    
    /**
     * 
     */
    private transient final Queue<String> output;

    /**
     * @param process
     */
    public ProcessTask(final Process process) {
        this.process = process;
        output = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void run() {
        try(InputStreamReader input = new InputStreamReader(process.getInputStream())){
            try(BufferedReader reader = new BufferedReader(input)){
                reader.lines().forEach(line -> {
                    output.add(line);
                });
            }
        } 
        catch (Exception ex) {
            LOGGER.severe(ex.getMessage());
        }
    }

    /**
     * 
     */
    public void close() {
        process.destroyForcibly();
    }

    public Process getProcess() {
        return process;
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
    
    public Queue<String> getOutput(){
    	return output;
    }
}
