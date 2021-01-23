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
public class Hover {
    private MarkupContent contents;
    private Optional<Range> range;

    public MarkupContent getContents() {
        return contents;
    }

    public void setContents(MarkupContent contents) {
        this.contents = contents;
    }

    public Optional<Range> getRange() {
        return range;
    }

    public void setRange(Optional<Range> range) {
        this.range = range;
    }
}
