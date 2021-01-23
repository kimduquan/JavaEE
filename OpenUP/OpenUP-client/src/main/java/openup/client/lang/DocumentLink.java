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
public class DocumentLink {
    private Range range;
    private Optional<DocumentUri> target;
    private Optional<String> tooltip;
    private Optional data;

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Optional<DocumentUri> getTarget() {
        return target;
    }

    public void setTarget(Optional<DocumentUri> target) {
        this.target = target;
    }

    public Optional<String> getTooltip() {
        return tooltip;
    }

    public void setTooltip(Optional<String> tooltip) {
        this.tooltip = tooltip;
    }

    public Optional getData() {
        return data;
    }

    public void setData(Optional data) {
        this.data = data;
    }
}
