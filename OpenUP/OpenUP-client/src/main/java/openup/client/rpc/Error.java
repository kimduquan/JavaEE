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
public class Error extends Exception {
    private Integer code;
    private String message;
    private Optional<Object> data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<Object> getData() {
        return data;
    }

    public void setData(Optional<Object> data) {
        this.data = data;
    }
}
