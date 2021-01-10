/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.identifier.TextDocumentIdentifier;

/**
 *
 * @author FOXCONN
 */
public class DidSaveTextDocumentParams {
    private TextDocumentIdentifier textDocument;
    private Optional<String> text;

    public TextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public Optional<String> getText() {
        return text;
    }

    public void setText(Optional<String> text) {
        this.text = text;
    }
}
