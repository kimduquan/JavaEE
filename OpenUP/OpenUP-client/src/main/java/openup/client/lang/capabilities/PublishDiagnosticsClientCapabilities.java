/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.DiagnosticTag;
import openup.client.lang.util.ValueSet;

/**
 *
 * @author FOXCONN
 */
public class PublishDiagnosticsClientCapabilities {
    private Optional<Boolean> relatedInformation;
    private Optional<ValueSet<DiagnosticTag>> tagSupport;
    private Optional<Boolean> versionSupport;
    private Optional<Boolean> codeDescriptionSupport;
    private Optional<Boolean> dataSupport;

    public Optional<Boolean> getRelatedInformation() {
        return relatedInformation;
    }

    public void setRelatedInformation(Optional<Boolean> relatedInformation) {
        this.relatedInformation = relatedInformation;
    }

    public Optional<ValueSet<DiagnosticTag>> getTagSupport() {
        return tagSupport;
    }

    public void setTagSupport(Optional<ValueSet<DiagnosticTag>> tagSupport) {
        this.tagSupport = tagSupport;
    }

    public Optional<Boolean> getVersionSupport() {
        return versionSupport;
    }

    public void setVersionSupport(Optional<Boolean> versionSupport) {
        this.versionSupport = versionSupport;
    }

    public Optional<Boolean> getCodeDescriptionSupport() {
        return codeDescriptionSupport;
    }

    public void setCodeDescriptionSupport(Optional<Boolean> codeDescriptionSupport) {
        this.codeDescriptionSupport = codeDescriptionSupport;
    }

    public Optional<Boolean> getDataSupport() {
        return dataSupport;
    }

    public void setDataSupport(Optional<Boolean> dataSupport) {
        this.dataSupport = dataSupport;
    }
}
