/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.Diagnostic;
import openup.client.lang.DocumentUri;

/**
 *
 * @author FOXCONN
 */
public class PublishDiagnosticsParams {
    private DocumentUri uri;
    private Optional<Integer> version;
    private Diagnostic[] diagnostics;

    public DocumentUri getUri() {
        return uri;
    }

    public void setUri(DocumentUri uri) {
        this.uri = uri;
    }

    public Optional<Integer> getVersion() {
        return version;
    }

    public void setVersion(Optional<Integer> version) {
        this.version = version;
    }

    public Diagnostic[] getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(Diagnostic[] diagnostics) {
        this.diagnostics = diagnostics;
    }
}
