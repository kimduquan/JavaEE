/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.FileEvent;

/**
 *
 * @author FOXCONN
 */
public class DidChangeWatchedFilesParams {
    private FileEvent[] changes;

    public FileEvent[] getChanges() {
        return changes;
    }

    public void setChanges(FileEvent[] changes) {
        this.changes = changes;
    }
}
