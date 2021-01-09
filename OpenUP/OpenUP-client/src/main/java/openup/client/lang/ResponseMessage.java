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
public class ResponseMessage extends Message {
    private String id;
    private Optional<Object> result;
    private Optional<ResponseError> error;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Optional<Object> getResult() {
        return result;
    }

    public void setResult(Optional<Object> result) {
        this.result = result;
    }

    public Optional<ResponseError> getError() {
        return error;
    }

    public void setError(Optional<ResponseError> error) {
        this.error = error;
    }
}
