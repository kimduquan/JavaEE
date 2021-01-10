/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.context;

import openup.client.lang.kind.SignatureHelpTriggerKind;
import java.util.Optional;
import openup.client.lang.SignatureHelp;

/**
 *
 * @author FOXCONN
 */
public class SignatureHelpContext {
    private SignatureHelpTriggerKind triggerKind;
    private Optional<String> triggerCharacter;
    private Boolean isRetrigger;
    private Optional<SignatureHelp> activeSignatureHelp;

    public SignatureHelpTriggerKind getTriggerKind() {
        return triggerKind;
    }

    public void setTriggerKind(SignatureHelpTriggerKind triggerKind) {
        this.triggerKind = triggerKind;
    }

    public Optional<String> getTriggerCharacter() {
        return triggerCharacter;
    }

    public void setTriggerCharacter(Optional<String> triggerCharacter) {
        this.triggerCharacter = triggerCharacter;
    }

    public Boolean getIsRetrigger() {
        return isRetrigger;
    }

    public void setIsRetrigger(Boolean isRetrigger) {
        this.isRetrigger = isRetrigger;
    }

    public Optional<SignatureHelp> getActiveSignatureHelp() {
        return activeSignatureHelp;
    }

    public void setActiveSignatureHelp(Optional<SignatureHelp> activeSignatureHelp) {
        this.activeSignatureHelp = activeSignatureHelp;
    }
}
