/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.Range;
import openup.client.lang.identifier.TextDocumentIdentifier;
import openup.client.lang.options.FormattingOptions;

/**
 *
 * @author FOXCONN
 */
public class DocumentRangeFormattingParams extends WorkDoneProgressParams {
    private TextDocumentIdentifier textDocument;
    private Range range;
    private FormattingOptions options;

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

    public FormattingOptions getOptions() {
        return options;
    }

    public void setOptions(FormattingOptions options) {
        this.options = options;
    }
}
