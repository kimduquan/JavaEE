/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class SignatureHelpOptions extends WorkDoneProgressOptions {
    private Optional<String[]> triggerCharacters;
    private Optional<String[]> retriggerCharacters;

    public Optional<String[]> getTriggerCharacters() {
        return triggerCharacters;
    }

    public void setTriggerCharacters(Optional<String[]> triggerCharacters) {
        this.triggerCharacters = triggerCharacters;
    }

    public Optional<String[]> getRetriggerCharacters() {
        return retriggerCharacters;
    }

    public void setRetriggerCharacters(Optional<String[]> retriggerCharacters) {
        this.retriggerCharacters = retriggerCharacters;
    }
}
