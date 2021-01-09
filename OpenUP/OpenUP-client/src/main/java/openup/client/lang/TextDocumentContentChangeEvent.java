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
public class TextDocumentContentChangeEvent {
    private Range range;
    private Optional<Integer> rangeLength;
    private String text;

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }

    public Optional<Integer> getRangeLength() {
        return rangeLength;
    }

    public void setRangeLength(Optional<Integer> rangeLength) {
        this.rangeLength = rangeLength;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
