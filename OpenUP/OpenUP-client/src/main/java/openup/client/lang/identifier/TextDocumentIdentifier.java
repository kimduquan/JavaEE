/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.identifier;

import openup.client.lang.DocumentUri;

/**
 *
 * @author FOXCONN
 */
public class TextDocumentIdentifier {
    private DocumentUri uri;

    public DocumentUri getUri() {
        return uri;
    }

    public void setUri(DocumentUri uri) {
        this.uri = uri;
    }
}
