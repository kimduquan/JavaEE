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
public class DocumentOnTypeFormattingOptions {
    private String firstTriggerCharacter;
    private Optional<String[]> moreTriggerCharacter;

    public String getFirstTriggerCharacter() {
        return firstTriggerCharacter;
    }

    public void setFirstTriggerCharacter(String firstTriggerCharacter) {
        this.firstTriggerCharacter = firstTriggerCharacter;
    }

    public Optional<String[]> getMoreTriggerCharacter() {
        return moreTriggerCharacter;
    }

    public void setMoreTriggerCharacter(Optional<String[]> moreTriggerCharacter) {
        this.moreTriggerCharacter = moreTriggerCharacter;
    }
}
