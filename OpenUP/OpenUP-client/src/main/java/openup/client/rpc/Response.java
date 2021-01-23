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
public class Response<T> extends Message {
    private String id;
    private Optional<T> result;
    private Optional<Error> error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Optional<T> getResult() {
        return result;
    }

    public void setResult(Optional<T> result) {
        this.result = result;
    }

    public Optional<Error> getError() {
        return error;
    }

    public void setError(Optional<Error> error) {
        this.error = error;
    }
}
