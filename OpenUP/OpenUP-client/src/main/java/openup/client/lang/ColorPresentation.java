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
public class ColorPresentation {
    private String label;
    private Optional<TextEdit> textEdit;
    private Optional<TextEdit[]> additionalTextEdits;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Optional<TextEdit> getTextEdit() {
        return textEdit;
    }

    public void setTextEdit(Optional<TextEdit> textEdit) {
        this.textEdit = textEdit;
    }

    public Optional<TextEdit[]> getAdditionalTextEdits() {
        return additionalTextEdits;
    }

    public void setAdditionalTextEdits(Optional<TextEdit[]> additionalTextEdits) {
        this.additionalTextEdits = additionalTextEdits;
    }
}
