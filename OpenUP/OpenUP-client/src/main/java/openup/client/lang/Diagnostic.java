/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.information.DiagnosticRelatedInformation;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class Diagnostic {
    private Range range;
    private Optional<DiagnosticSeverity> severity;
    private Optional<String> code;
    private Optional<CodeDescription> codeDescription;
    private Optional<String> source;
    private String message;
    private Optional<DiagnosticTag[]> tags;
    private Optional<DiagnosticRelatedInformation[]> relatedInformation;
    private Optional<?> data;

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Optional<DiagnosticSeverity> getSeverity() {
        return severity;
    }

    public void setSeverity(Optional<DiagnosticSeverity> severity) {
        this.severity = severity;
    }

    public Optional<String> getCode() {
        return code;
    }

    public void setCode(Optional<String> code) {
        this.code = code;
    }

    public Optional<CodeDescription> getCodeDescription() {
        return codeDescription;
    }

    public void setCodeDescription(Optional<CodeDescription> codeDescription) {
        this.codeDescription = codeDescription;
    }

    public Optional<String> getSource() {
        return source;
    }

    public void setSource(Optional<String> source) {
        this.source = source;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Optional<DiagnosticTag[]> getTags() {
        return tags;
    }

    public void setTags(Optional<DiagnosticTag[]> tags) {
        this.tags = tags;
    }

    public Optional<DiagnosticRelatedInformation[]> getRelatedInformation() {
        return relatedInformation;
    }

    public void setRelatedInformation(Optional<DiagnosticRelatedInformation[]> relatedInformation) {
        this.relatedInformation = relatedInformation;
    }

    public Optional<?> getData() {
        return data;
    }

    public void setData(Optional<?> data) {
        this.data = data;
    }
}
