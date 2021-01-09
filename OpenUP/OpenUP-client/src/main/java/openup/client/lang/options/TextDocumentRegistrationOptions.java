/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import openup.client.lang.DocumentSelector;

/**
 *
 * @author FOXCONN
 */
public class TextDocumentRegistrationOptions {
    private DocumentSelector documentSelector;

    public DocumentSelector getDocumentSelector() {
        return documentSelector;
    }

    public void setDocumentSelector(DocumentSelector documentSelector) {
        this.documentSelector = documentSelector;
    }
}
