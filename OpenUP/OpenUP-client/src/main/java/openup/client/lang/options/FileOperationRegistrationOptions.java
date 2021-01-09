/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import openup.client.lang.FileOperationFilter;

/**
 *
 * @author FOXCONN
 */
public class FileOperationRegistrationOptions {
    private FileOperationFilter[] filters;

    public FileOperationFilter[] getFilters() {
        return filters;
    }

    public void setFilters(FileOperationFilter[] filters) {
        this.filters = filters;
    }
}
