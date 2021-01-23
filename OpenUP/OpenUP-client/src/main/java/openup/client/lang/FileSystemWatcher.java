/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.kind.WatchKind;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class FileSystemWatcher {
    private String globPattern;
    private Optional<WatchKind> kind;

    public String getGlobPattern() {
        return globPattern;
    }

    public void setGlobPattern(String globPattern) {
        this.globPattern = globPattern;
    }

    public Optional<WatchKind> getKind() {
        return kind;
    }

    public void setKind(Optional<WatchKind> kind) {
        this.kind = kind;
    }
}
