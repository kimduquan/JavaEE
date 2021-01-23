/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options.registration;

import openup.client.lang.FileSystemWatcher;

/**
 *
 * @author FOXCONN
 */
public class DidChangeWatchedFilesRegistrationOptions {
    private FileSystemWatcher[] watchers;

    public FileSystemWatcher[] getWatchers() {
        return watchers;
    }

    public void setWatchers(FileSystemWatcher[] watchers) {
        this.watchers = watchers;
    }
}
