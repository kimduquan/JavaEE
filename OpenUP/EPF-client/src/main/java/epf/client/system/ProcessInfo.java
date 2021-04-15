/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.system;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

/**
 *
 * @author FOXCONN
 */
public class ProcessInfo {
    /**
     * 
     */
    private String[] arguments;
    /**
     * 
     */
    private String command;
    /**
     * 
     */
    private String commandLine;
    /**
     * 
     */
    private Instant start;
    /**
     * 
     */
    private Duration totalCpu;
    /**
     * 
     */
    private String user;

    public String[] getArguments() {
    	return arguments;
    }

    public void setArguments(final String... arguments) {
        this.arguments = Arrays.copyOf(arguments, arguments.length);
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(final String commandLine) {
        this.commandLine = commandLine;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(final Instant start) {
        this.start = start;
    }

    public Duration getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(final Duration totalCpu) {
        this.totalCpu = totalCpu;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }
}
