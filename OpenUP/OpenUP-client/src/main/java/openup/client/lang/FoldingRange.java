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
public class FoldingRange {
    private Integer startLine;
    private Optional<Integer> startCharacter;
    private Integer endLine;
    private Optional<Integer> endCharacter;
    private Optional<String> kind;

    public Integer getStartLine() {
        return startLine;
    }

    public void setStartLine(Integer startLine) {
        this.startLine = startLine;
    }

    public Optional<Integer> getStartCharacter() {
        return startCharacter;
    }

    public void setStartCharacter(Optional<Integer> startCharacter) {
        this.startCharacter = startCharacter;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public void setEndLine(Integer endLine) {
        this.endLine = endLine;
    }

    public Optional<Integer> getEndCharacter() {
        return endCharacter;
    }

    public void setEndCharacter(Optional<Integer> endCharacter) {
        this.endCharacter = endCharacter;
    }

    public Optional<String> getKind() {
        return kind;
    }

    public void setKind(Optional<String> kind) {
        this.kind = kind;
    }
}
