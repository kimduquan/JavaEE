/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

/**
 *
 * @author FOXCONN
 */
public class WorkspaceFoldersChangeEvent {
    private WorkspaceFolder[] added;
    private WorkspaceFolder[] removed;

    public WorkspaceFolder[] getAdded() {
        return added;
    }

    public void setAdded(WorkspaceFolder[] added) {
        this.added = added;
    }

    public WorkspaceFolder[] getRemoved() {
        return removed;
    }

    public void setRemoved(WorkspaceFolder[] removed) {
        this.removed = removed;
    }
}
