/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import java.util.Optional;
import openup.client.lang.SemanticTokensLegend;

/**
 *
 * @author FOXCONN
 */
public class SemanticTokensOptions extends WorkDoneProgressOptions {
    private SemanticTokensLegend legend;
    private Optional<Boolean> range;
    private Optional<Boolean> full;

    public SemanticTokensLegend getLegend() {
        return legend;
    }

    public void setLegend(SemanticTokensLegend legend) {
        this.legend = legend;
    }

    public Optional<Boolean> getRange() {
        return range;
    }

    public void setRange(Optional<Boolean> range) {
        this.range = range;
    }

    public Optional<Boolean> getFull() {
        return full;
    }

    public void setFull(Optional<Boolean> full) {
        this.full = full;
    }
}
