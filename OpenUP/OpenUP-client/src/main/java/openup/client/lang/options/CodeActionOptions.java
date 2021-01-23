/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import java.util.Optional;
import openup.client.lang.kind.CodeActionKind;

/**
 *
 * @author FOXCONN
 */
public class CodeActionOptions extends WorkDoneProgressOptions {
    private Optional<CodeActionKind[]> codeActionKinds;
    private Optional<Boolean> resolveProvider;

    public Optional<CodeActionKind[]> getCodeActionKinds() {
        return codeActionKinds;
    }

    public void setCodeActionKinds(Optional<CodeActionKind[]> codeActionKinds) {
        this.codeActionKinds = codeActionKinds;
    }

    public Optional<Boolean> getResolveProvider() {
        return resolveProvider;
    }

    public void setResolveProvider(Optional<Boolean> resolveProvider) {
        this.resolveProvider = resolveProvider;
    }
}
