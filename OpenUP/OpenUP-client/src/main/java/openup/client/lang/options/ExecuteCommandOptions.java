/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

/**
 *
 * @author FOXCONN
 */
public class ExecuteCommandOptions extends WorkDoneProgressOptions {
    private String[] commands;

    public String[] getCommands() {
        return commands;
    }

    public void setCommands(String[] commands) {
        this.commands = commands;
    }
}
