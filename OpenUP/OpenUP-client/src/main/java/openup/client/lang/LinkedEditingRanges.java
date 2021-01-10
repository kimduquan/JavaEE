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
public class LinkedEditingRanges {
    private Range[] ranges;
    private Optional<String> wordPattern;

    public Range[] getRanges() {
        return ranges;
    }

    public void setRanges(Range[] ranges) {
        this.ranges = ranges;
    }

    public Optional<String> getWordPattern() {
        return wordPattern;
    }

    public void setWordPattern(Optional<String> wordPattern) {
        this.wordPattern = wordPattern;
    }
}
