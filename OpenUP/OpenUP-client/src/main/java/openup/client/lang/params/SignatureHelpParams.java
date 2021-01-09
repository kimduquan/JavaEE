/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import java.util.Optional;
import openup.client.lang.SignatureHelpContext;

/**
 *
 * @author FOXCONN
 */
public class SignatureHelpParams extends WorkDoneProgressParams {
    private Optional<SignatureHelpContext> context;

    public Optional<SignatureHelpContext> getContext() {
        return context;
    }

    public void setContext(Optional<SignatureHelpContext> context) {
        this.context = context;
    }
}
