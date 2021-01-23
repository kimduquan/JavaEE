/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.context.CodeActionContext;
import openup.client.lang.Range;
import openup.client.lang.identifier.TextDocumentIdentifier;

/**
 *
 * @author FOXCONN
 */
public class CodeActionParams extends WorkDoneProgressParams{
    private TextDocumentIdentifier textDocument;
    private Range range;
    private CodeActionContext context;

    public TextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public CodeActionContext getContext() {
        return context;
    }

    public void setContext(CodeActionContext context) {
        this.context = context;
    }
}
