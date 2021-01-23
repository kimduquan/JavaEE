/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

/**
 *
 * @author FOXCONN
 */
public class InitializeError extends Error {
    public static final int unknownProtocolVersion = 1;
    private Boolean retry;

    public Boolean getRetry() {
        return retry;
    }

    public void setRetry(Boolean retry) {
        this.retry = retry;
    }
}
