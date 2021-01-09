/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class WorkDoneProgressOptions {
    private Optional<Boolean> workDoneProgress;

    public Optional<Boolean> getWorkDoneProgress() {
        return workDoneProgress;
    }

    public void setWorkDoneProgress(Optional<Boolean> workDoneProgress) {
        this.workDoneProgress = workDoneProgress;
    }
}
