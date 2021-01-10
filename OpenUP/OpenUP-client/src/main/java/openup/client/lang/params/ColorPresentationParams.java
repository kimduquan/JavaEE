/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.Color;
import openup.client.lang.Range;
import openup.client.lang.identifier.TextDocumentIdentifier;

/**
 *
 * @author FOXCONN
 */
public class ColorPresentationParams extends WorkDoneProgressParams {
    private TextDocumentIdentifier textDocument;
    private Color color;
    private Range range;

    public TextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}
