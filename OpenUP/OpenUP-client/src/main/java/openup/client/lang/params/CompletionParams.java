/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.CompletionContext;

/**
 *
 * @author FOXCONN
 */
public class CompletionParams extends WorkDoneProgressParams {
    private Optional<CompletionContext> context;

    public Optional<CompletionContext> getContext() {
        return context;
    }

    public void setContext(Optional<CompletionContext> context) {
        this.context = context;
    }
}
