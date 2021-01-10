/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

/**
 *
 * @author FOXCONN
 */
public class SemanticTokensDeltaPartialResult {
    private SemanticTokensEdit[] edits;

    public SemanticTokensEdit[] getEdits() {
        return edits;
    }

    public void setEdits(SemanticTokensEdit[] edits) {
        this.edits = edits;
    }
}
