/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.rpc;

import java.util.Optional;
import javax.enterprise.context.Dependent;

/**
 *
 * @author FOXCONN
 */
@Dependent
public class Request<T> extends Message {
    private String id;
    private String method;
    private Optional<T> params;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Optional<T> getParams() {
        return params;
    }

    public void setParams(Optional<T> params) {
        this.params = params;
    }
}
