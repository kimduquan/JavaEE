/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.TextDocumentContentChangeEvent;
import openup.client.lang.identifier.VersionedTextDocumentIdentifier;

/**
 *
 * @author FOXCONN
 */
public class DidChangeTextDocumentParams {
    private VersionedTextDocumentIdentifier textDocument;
    private TextDocumentContentChangeEvent[] contentChanges;

    public VersionedTextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(VersionedTextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public TextDocumentContentChangeEvent[] getContentChanges() {
        return contentChanges;
    }

    public void setContentChanges(TextDocumentContentChangeEvent[] contentChanges) {
        this.contentChanges = contentChanges;
    }
}
