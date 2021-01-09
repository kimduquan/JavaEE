/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.WorkspaceEdit;

/**
 *
 * @author FOXCONN
 */
public class ApplyWorkspaceEditParams {
    private Optional<String> label;
    private WorkspaceEdit edit;

    public Optional<String> getLabel() {
        return label;
    }

    public void setLabel(Optional<String> label) {
        this.label = label;
    }

    public WorkspaceEdit getEdit() {
        return edit;
    }

    public void setEdit(WorkspaceEdit edit) {
        this.edit = edit;
    }
}
