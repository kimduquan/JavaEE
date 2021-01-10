/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.identifier.ChangeAnnotationIdentifier;

/**
 *
 * @author FOXCONN
 */
public class AnnotatedTextEdit extends TextEdit {
    private ChangeAnnotationIdentifier annotationId;

    public ChangeAnnotationIdentifier getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(ChangeAnnotationIdentifier annotationId) {
        this.annotationId = annotationId;
    }
}
