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
public class TextDocumentSaveRegistrationOptions extends TextDocumentRegistrationOptions {
    private Optional<Boolean> includeText;

    public Optional<Boolean> getIncludeText() {
        return includeText;
    }

    public void setIncludeText(Optional<Boolean> includeText) {
        this.includeText = includeText;
    }
}
