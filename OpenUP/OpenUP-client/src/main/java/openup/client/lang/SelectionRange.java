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
public class SelectionRange {
    private Range range;
    private Optional<SelectionRange> parent;

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Optional<SelectionRange> getParent() {
        return parent;
    }

    public void setParent(Optional<SelectionRange> parent) {
        this.parent = parent;
    }
}
