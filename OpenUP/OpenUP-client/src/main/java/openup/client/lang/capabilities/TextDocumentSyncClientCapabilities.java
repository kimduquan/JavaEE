/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class TextDocumentSyncClientCapabilities {
    private Optional<Boolean> dynamicRegistration;
    private Optional<Boolean> willSave;
    private Optional<Boolean> willSaveWaitUntil;
    private Optional<Boolean> didSave;

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Optional<Boolean> getWillSave() {
        return willSave;
    }

    public void setWillSave(Optional<Boolean> willSave) {
        this.willSave = willSave;
    }

    public Optional<Boolean> getWillSaveWaitUntil() {
        return willSaveWaitUntil;
    }

    public void setWillSaveWaitUntil(Optional<Boolean> willSaveWaitUntil) {
        this.willSaveWaitUntil = willSaveWaitUntil;
    }

    public Optional<Boolean> getDidSave() {
        return didSave;
    }

    public void setDidSave(Optional<Boolean> didSave) {
        this.didSave = didSave;
    }
}
