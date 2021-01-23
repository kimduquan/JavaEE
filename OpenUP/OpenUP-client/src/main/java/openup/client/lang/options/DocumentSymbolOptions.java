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
public class DocumentSymbolOptions extends WorkDoneProgressOptions {
    private Optional<String> label;

    public Optional<String> getLabel() {
        return label;
    }

    public void setLabel(Optional<String> label) {
        this.label = label;
    }
}
