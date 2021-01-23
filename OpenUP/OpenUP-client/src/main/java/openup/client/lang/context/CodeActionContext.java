/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.context;

import openup.client.lang.kind.CodeActionKind;
import java.util.Optional;
import openup.client.lang.Diagnostic;

/**
 *
 * @author FOXCONN
 */
public class CodeActionContext {
    private Diagnostic[] diagnostics;
    private Optional<CodeActionKind[]> only;

    public Diagnostic[] getDiagnostics() {
        return diagnostics;
    }

    public void setDiagnostics(Diagnostic[] diagnostics) {
        this.diagnostics = diagnostics;
    }

    public Optional<CodeActionKind[]> getOnly() {
        return only;
    }

    public void setOnly(Optional<CodeActionKind[]> only) {
        this.only = only;
    }
}
