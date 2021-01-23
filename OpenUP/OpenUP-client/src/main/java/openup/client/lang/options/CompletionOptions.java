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
public class CompletionOptions extends WorkDoneProgressOptions {
    private Optional<String[]> triggerCharacters;
    private Optional<String[]> allCommitCharacters;
    private Optional<Boolean> resolveProvider;

    public Optional<String[]> getTriggerCharacters() {
        return triggerCharacters;
    }

    public void setTriggerCharacters(Optional<String[]> triggerCharacters) {
        this.triggerCharacters = triggerCharacters;
    }

    public Optional<String[]> getAllCommitCharacters() {
        return allCommitCharacters;
    }

    public void setAllCommitCharacters(Optional<String[]> allCommitCharacters) {
        this.allCommitCharacters = allCommitCharacters;
    }

    public Optional<Boolean> getResolveProvider() {
        return resolveProvider;
    }

    public void setResolveProvider(Optional<Boolean> resolveProvider) {
        this.resolveProvider = resolveProvider;
    }
}
