/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class ExecuteCommandParams extends WorkDoneProgressParams {
    private String command;
    private Optional<Object[]> arguments;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Optional<Object[]> getArguments() {
        return arguments;
    }

    public void setArguments(Optional<Object[]> arguments) {
        this.arguments = arguments;
    }
}
