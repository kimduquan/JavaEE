/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.options.DeleteFileOptions;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class DeleteFile {
    private final String kind = "delete";
    private DocumentUri uri;
    private Optional<DeleteFileOptions> options;
    private Optional<ChangeAnnotationIdentifier> annotationId;
    
    public String getKind(){
        return kind;
    }

    public DocumentUri getUri() {
        return uri;
    }

    public void setUri(DocumentUri uri) {
        this.uri = uri;
    }

    public Optional<DeleteFileOptions> getOptions() {
        return options;
    }

    public void setOptions(Optional<DeleteFileOptions> options) {
        this.options = options;
    }

    public Optional<ChangeAnnotationIdentifier> getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(Optional<ChangeAnnotationIdentifier> annotationId) {
        this.annotationId = annotationId;
    }
}
