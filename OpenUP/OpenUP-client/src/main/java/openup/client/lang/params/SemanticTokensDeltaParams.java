/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.identifier.TextDocumentIdentifier;

/**
 *
 * @author FOXCONN
 */
public class SemanticTokensDeltaParams extends WorkDoneProgressParams {
    private TextDocumentIdentifier textDocument;
    private String previousResultId;

    public TextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public String getPreviousResultId() {
        return previousResultId;
    }

    public void setPreviousResultId(String previousResultId) {
        this.previousResultId = previousResultId;
    }
}
