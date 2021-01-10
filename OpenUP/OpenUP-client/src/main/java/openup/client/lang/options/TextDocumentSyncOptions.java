/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import java.util.Optional;
import openup.client.lang.kind.TextDocumentSyncKind;

/**
 *
 * @author FOXCONN
 */
public class TextDocumentSyncOptions {
    private Optional<Boolean> openClose;
    private Optional<TextDocumentSyncKind> change;
    private Optional<Boolean> willSave;
    private Optional<Boolean> willSaveWaitUntil;
    private Optional<SaveOptions> save;

    public Optional<Boolean> getOpenClose() {
        return openClose;
    }

    public void setOpenClose(Optional<Boolean> openClose) {
        this.openClose = openClose;
    }

    public Optional<TextDocumentSyncKind> getChange() {
        return change;
    }

    public void setChange(Optional<TextDocumentSyncKind> change) {
        this.change = change;
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

    public Optional<SaveOptions> getSave() {
        return save;
    }

    public void setSave(Optional<SaveOptions> save) {
        this.save = save;
    }
}
