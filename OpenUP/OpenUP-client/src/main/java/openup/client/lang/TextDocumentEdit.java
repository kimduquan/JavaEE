/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.identifier.OptionalVersionedTextDocumentIdentifier;

/**
 *
 * @author FOXCONN
 */
public class TextDocumentEdit {
    private OptionalVersionedTextDocumentIdentifier textDocument;
    private TextEdit[] edits;

    public OptionalVersionedTextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(OptionalVersionedTextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public TextEdit[] getEdits() {
        return edits;
    }

    public void setEdits(TextEdit[] edits) {
        this.edits = edits;
    }
}
