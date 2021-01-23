/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class DeleteFileOptions {
    private Optional<Boolean> recursive;
    private Optional<Boolean> ignoreIfNotExists;

    public Optional<Boolean> getRecursive() {
        return recursive;
    }

    public void setRecursive(Optional<Boolean> recursive) {
        this.recursive = recursive;
    }

    public Optional<Boolean> getIgnoreIfNotExists() {
        return ignoreIfNotExists;
    }

    public void setIgnoreIfNotExists(Optional<Boolean> ignoreIfNotExists) {
        this.ignoreIfNotExists = ignoreIfNotExists;
    }
}
