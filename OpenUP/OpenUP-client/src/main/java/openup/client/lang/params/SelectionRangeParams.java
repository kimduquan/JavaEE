/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.Position;
import openup.client.lang.identifier.TextDocumentIdentifier;

/**
 *
 * @author FOXCONN
 */
public class SelectionRangeParams extends WorkDoneProgressParams {
    private TextDocumentIdentifier textDocument;
    private Position[] positions;

    public TextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public Position[] getPositions() {
        return positions;
    }

    public void setPositions(Position[] positions) {
        this.positions = positions;
    }
}
