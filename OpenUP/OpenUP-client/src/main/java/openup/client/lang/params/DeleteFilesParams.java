/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.FileDelete;

/**
 *
 * @author FOXCONN
 */
public class DeleteFilesParams {
    private FileDelete[] files;

    public FileDelete[] getFiles() {
        return files;
    }

    public void setFiles(FileDelete[] files) {
        this.files = files;
    }
}
