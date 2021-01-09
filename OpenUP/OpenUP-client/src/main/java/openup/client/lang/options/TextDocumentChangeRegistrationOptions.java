/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import openup.client.lang.TextDocumentSyncKind;

/**
 *
 * @author FOXCONN
 */
public class TextDocumentChangeRegistrationOptions extends TextDocumentRegistrationOptions {
    private TextDocumentSyncKind syncKind;

    public TextDocumentSyncKind getSyncKind() {
        return syncKind;
    }

    public void setSyncKind(TextDocumentSyncKind syncKind) {
        this.syncKind = syncKind;
    }
}
