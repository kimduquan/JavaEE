/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.FileRename;

/**
 *
 * @author FOXCONN
 */
public class RenameFilesParams {
    private FileRename[] files;

    public FileRename[] getFiles() {
        return files;
    }

    public void setFiles(FileRename[] files) {
        this.files = files;
    }
}
