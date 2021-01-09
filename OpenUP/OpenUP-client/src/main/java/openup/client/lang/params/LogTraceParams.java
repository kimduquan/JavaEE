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
public class LogTraceParams {
    private String message;
    private Optional<String> verbose;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<String> getVerbose() {
        return verbose;
    }

    public void setVerbose(Optional<String> verbose) {
        this.verbose = verbose;
    }
}
