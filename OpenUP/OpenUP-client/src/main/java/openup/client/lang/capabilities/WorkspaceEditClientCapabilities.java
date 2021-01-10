/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.kind.FailureHandlingKind;
import openup.client.lang.kind.ResourceOperationKind;

/**
 *
 * @author FOXCONN
 */
public class WorkspaceEditClientCapabilities {
    private Optional<Boolean> documentChanges;
    private Optional<ResourceOperationKind[]> resourceOperations;
    private Optional<FailureHandlingKind> failureHandling;
    private Optional<Boolean> normalizesLineEndings;
    private Optional<Boolean> changeAnnotationSupport;
    private Optional<Boolean> groupsOnLabel;

    public Optional<Boolean> getDocumentChanges() {
        return documentChanges;
    }

    public void setDocumentChanges(Optional<Boolean> documentChanges) {
        this.documentChanges = documentChanges;
    }

    public Optional<ResourceOperationKind[]> getResourceOperations() {
        return resourceOperations;
    }

    public void setResourceOperations(Optional<ResourceOperationKind[]> resourceOperations) {
        this.resourceOperations = resourceOperations;
    }

    public Optional<FailureHandlingKind> getFailureHandling() {
        return failureHandling;
    }

    public void setFailureHandling(Optional<FailureHandlingKind> failureHandling) {
        this.failureHandling = failureHandling;
    }

    public Optional<Boolean> getNormalizesLineEndings() {
        return normalizesLineEndings;
    }

    public void setNormalizesLineEndings(Optional<Boolean> normalizesLineEndings) {
        this.normalizesLineEndings = normalizesLineEndings;
    }

    public Optional<Boolean> getChangeAnnotationSupport() {
        return changeAnnotationSupport;
    }

    public void setChangeAnnotationSupport(Optional<Boolean> changeAnnotationSupport) {
        this.changeAnnotationSupport = changeAnnotationSupport;
    }

    public Optional<Boolean> getGroupsOnLabel() {
        return groupsOnLabel;
    }

    public void setGroupsOnLabel(Optional<Boolean> groupsOnLabel) {
        this.groupsOnLabel = groupsOnLabel;
    }
}
