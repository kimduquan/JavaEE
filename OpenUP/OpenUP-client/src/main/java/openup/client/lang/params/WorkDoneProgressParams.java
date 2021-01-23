/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.ProgressToken;

/**
 *
 * @author FOXCONN
 */
public class WorkDoneProgressParams {
    private Optional<ProgressToken> workDoneToken;

    public Optional<ProgressToken> getWorkDoneToken() {
        return workDoneToken;
    }

    public void setWorkDoneToken(Optional<ProgressToken> workDoneToken) {
        this.workDoneToken = workDoneToken;
    }
}
