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
public class CreateFileOptions {
    private Optional<Boolean> overwrite;
    private Optional<Boolean> ignoreIfExists;

    public Optional<Boolean> getOverwrite() {
        return overwrite;
    }

    public void setOverwrite(Optional<Boolean> overwrite) {
        this.overwrite = overwrite;
    }

    public Optional<Boolean> getIgnoreIfExists() {
        return ignoreIfExists;
    }

    public void setIgnoreIfExists(Optional<Boolean> ignoreIfExists) {
        this.ignoreIfExists = ignoreIfExists;
    }
}
