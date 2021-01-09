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
public class FileOperationFilter {
    private Optional<String> scheme;
    private FileOperationPattern pattern;

    public Optional<String> getScheme() {
        return scheme;
    }

    public void setScheme(Optional<String> scheme) {
        this.scheme = scheme;
    }

    public FileOperationPattern getPattern() {
        return pattern;
    }

    public void setPattern(FileOperationPattern pattern) {
        this.pattern = pattern;
    }
}
