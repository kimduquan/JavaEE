/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class CodeLens {
    private Range range;
    private Optional<Command> command;
    private Optional<?> data;

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Optional<Command> getCommand() {
        return command;
    }

    public void setCommand(Optional<Command> command) {
        this.command = command;
    }

    public Optional<?> getData() {
        return data;
    }

    public void setData(Optional<?> data) {
        this.data = data;
    }
}
