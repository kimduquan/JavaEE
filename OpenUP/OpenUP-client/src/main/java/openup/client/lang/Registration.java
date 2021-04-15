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
public class Registration {
    private String id;
    private String method;
    private Optional<?> registerOptions;

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

    public Optional<?> getRegisterOptions() {
        return registerOptions;
    }

    public void setRegisterOptions(Optional<?> registerOptions) {
        this.registerOptions = registerOptions;
    }
}
