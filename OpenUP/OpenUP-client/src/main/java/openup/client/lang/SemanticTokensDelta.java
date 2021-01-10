/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class SemanticTokensDelta {
    private Optional<String> resultId;
    private SemanticTokensEdit[] edits;

    public Optional<String> getResultId() {
        return resultId;
    }

    public void setResultId(Optional<String> resultId) {
        this.resultId = resultId;
    }

    public SemanticTokensEdit[] getEdits() {
        return edits;
    }

    public void setEdits(SemanticTokensEdit[] edits) {
        this.edits = edits;
    }
}
