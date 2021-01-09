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
public class FileOperationPatternOptions {
    private Optional<Boolean> ignoreCase;

    public Optional<Boolean> getIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(Optional<Boolean> ignoreCase) {
        this.ignoreCase = ignoreCase;
    }
}
