/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.ProgressToken;

/**
 *
 * @author FOXCONN
 */
public class WorkDoneProgressCancelParams {
    private ProgressToken token;

    public ProgressToken getToken() {
        return token;
    }

    public void setToken(ProgressToken token) {
        this.token = token;
    }
}
