/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.FileCreate;

/**
 *
 * @author FOXCONN
 */
public class CreateFilesParams {
    private FileCreate[] files;

    public FileCreate[] getFiles() {
        return files;
    }

    public void setFiles(FileCreate[] files) {
        this.files = files;
    }
}
