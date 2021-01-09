/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.options.RenameFileOptions;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class RenameFile {
    private final String kind = "rename";
    private DocumentUri oldUri;
    private DocumentUri newUri;
    private Optional<RenameFileOptions> options;
    private Optional<ChangeAnnotationIdentifier> annotationId;

    public String getKind() {
        return kind;
    }

    public DocumentUri getOldUri() {
        return oldUri;
    }

    public void setOldUri(DocumentUri oldUri) {
        this.oldUri = oldUri;
    }

    public DocumentUri getNewUri() {
        return newUri;
    }

    public void setNewUri(DocumentUri newUri) {
        this.newUri = newUri;
    }

    public Optional<RenameFileOptions> getOptions() {
        return options;
    }

    public void setOptions(Optional<RenameFileOptions> options) {
        this.options = options;
    }

    public Optional<ChangeAnnotationIdentifier> getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(Optional<ChangeAnnotationIdentifier> annotationId) {
        this.annotationId = annotationId;
    }
}
