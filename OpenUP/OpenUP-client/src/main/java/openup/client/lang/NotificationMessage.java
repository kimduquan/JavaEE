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
public class NotificationMessage extends Message {
    private String method;
    private Optional<Object> params;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Optional<Object> getParams() {
        return params;
    }

    public void setParams(Optional<Object> params) {
        this.params = params;
    }
}
