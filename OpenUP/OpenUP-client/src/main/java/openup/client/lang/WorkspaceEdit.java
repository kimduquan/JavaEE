/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import java.util.Map;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class WorkspaceEdit {
    private Optional<Map<DocumentUri, TextEdit[]>> changes;
    private Optional<TextDocumentEdit> documentChanges;
    private Optional<Map<String, ChangeAnnotation>> changeAnnotations;

    public Optional<Map<DocumentUri, TextEdit[]>> getChanges() {
        return changes;
    }

    public void setChanges(Optional<Map<DocumentUri, TextEdit[]>> changes) {
        this.changes = changes;
    }

    public Optional<TextDocumentEdit> getDocumentChanges() {
        return documentChanges;
    }

    public void setDocumentChanges(Optional<TextDocumentEdit> documentChanges) {
        this.documentChanges = documentChanges;
    }

    public Optional<Map<String, ChangeAnnotation>> getChangeAnnotations() {
        return changeAnnotations;
    }

    public void setChangeAnnotations(Optional<Map<String, ChangeAnnotation>> changeAnnotations) {
        this.changeAnnotations = changeAnnotations;
    }
}
