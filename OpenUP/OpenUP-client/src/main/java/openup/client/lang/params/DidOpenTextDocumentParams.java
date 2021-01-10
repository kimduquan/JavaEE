/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.item.TextDocumentItem;

/**
 *
 * @author FOXCONN
 */
public class DidOpenTextDocumentParams {
    private TextDocumentItem textDocument;

    public TextDocumentItem getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocumentItem textDocument) {
        this.textDocument = textDocument;
    }
}
