/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.identifier.TextDocumentIdentifier;
import openup.client.lang.TextDocumentSaveReason;

/**
 *
 * @author FOXCONN
 */
public class WillSaveTextDocumentParams {
    private TextDocumentIdentifier textDocument;
    private TextDocumentSaveReason reason;

    public TextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public TextDocumentSaveReason getReason() {
        return reason;
    }

    public void setReason(TextDocumentSaveReason reason) {
        this.reason = reason;
    }
}
