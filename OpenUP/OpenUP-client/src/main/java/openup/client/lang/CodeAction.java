/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.kind.CodeActionKind;
import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class CodeAction {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Optional<CodeActionKind> getKind() {
        return kind;
    }

    public void setKind(Optional<CodeActionKind> kind) {
        this.kind = kind;
    }

    public Optional<Diagnostic[]> getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(Optional<Diagnostic[]> diagnostics) {
        this.diagnostics = diagnostics;
    }

    public Optional<Boolean> getIsPreferred() {
        return isPreferred;
    }

    public void setIsPreferred(Optional<Boolean> isPreferred) {
        this.isPreferred = isPreferred;
    }

    public Optional<Description> getDisabled() {
        return disabled;
    }

    public void setDisabled(Optional<Description> disabled) {
        this.disabled = disabled;
    }

    public Optional<WorkspaceEdit> getEdit() {
        return edit;
    }

    public void setEdit(Optional<WorkspaceEdit> edit) {
        this.edit = edit;
    }

    public Optional<Command> getCommand() {
        return command;
    }

    public void setCommand(Optional<Command> command) {
        this.command = command;
    }

    public Optional getData() {
        return data;
    }

    public void setData(Optional data) {
        this.data = data;
    }
    public class Description {
        private String reason;

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
    private String title;
    private Optional<CodeActionKind> kind;
    private Optional<Diagnostic[]> diagnostics;
    private Optional<Boolean> isPreferred;
    private Optional<Description> disabled;
    private Optional<WorkspaceEdit> edit;
    private Optional<Command> command;
    private Optional data;
}
