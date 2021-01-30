/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.runtime;

import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author FOXCONN
 */
public class ProcessInfo {
    private String[] arguments;
    private String command;
    private String commandLine;
    private Instant start;
    private Duration totalCpu;
    private String user;

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommandLine() {
        return commandLine;
    }

    public void setCommandLine(String commandLine) {
        this.commandLine = commandLine;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Duration getTotalCpu() {
        return totalCpu;
    }

    public void setTotalCpu(Duration totalCpu) {
        this.totalCpu = totalCpu;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
