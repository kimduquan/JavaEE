/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class CompletionContext {
    private CompletionTriggerKind triggerKind;
    private Optional<String> triggerCharacter;

    public CompletionTriggerKind getTriggerKind() {
        return triggerKind;
    }

    public void setTriggerKind(CompletionTriggerKind triggerKind) {
        this.triggerKind = triggerKind;
    }

    public Optional<String> getTriggerCharacter() {
        return triggerCharacter;
    }

    public void setTriggerCharacter(Optional<String> triggerCharacter) {
        this.triggerCharacter = triggerCharacter;
    }
}
