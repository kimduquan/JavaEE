/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.kind.FileOperationPatternKind;
import openup.client.lang.options.FileOperationPatternOptions;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class FileOperationPattern {
    private String glob;
    private Optional<FileOperationPatternKind> matches;
    private Optional<FileOperationPatternOptions> options;

    public String getGlob() {
        return glob;
    }

    public void setGlob(String glob) {
        this.glob = glob;
    }

    public Optional<FileOperationPatternKind> getMatches() {
        return matches;
    }

    public void setMatches(Optional<FileOperationPatternKind> matches) {
        this.matches = matches;
    }

    public Optional<FileOperationPatternOptions> getOptions() {
        return options;
    }

    public void setOptions(Optional<FileOperationPatternOptions> options) {
        this.options = options;
    }
}
