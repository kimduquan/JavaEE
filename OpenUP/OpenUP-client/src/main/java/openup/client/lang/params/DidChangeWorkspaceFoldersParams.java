/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.WorkspaceFoldersChangeEvent;

/**
 *
 * @author FOXCONN
 */
public class DidChangeWorkspaceFoldersParams {
    private WorkspaceFoldersChangeEvent event;

    public WorkspaceFoldersChangeEvent getEvent() {
        return event;
    }

    public void setEvent(WorkspaceFoldersChangeEvent event) {
        this.event = event;
    }
}
