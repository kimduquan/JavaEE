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
public class ApplyWorkspaceEditResponse {
    private Boolean applied;
    private Optional<String> failureReason;
    private Optional<Integer> failedChange;

    public Boolean getApplied() {
        return applied;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }

    public Optional<String> getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(Optional<String> failureReason) {
        this.failureReason = failureReason;
    }

    public Optional<Integer> getFailedChange() {
        return failedChange;
    }

    public void setFailedChange(Optional<Integer> failedChange) {
        this.failedChange = failedChange;
    }
}
