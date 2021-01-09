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
public class ChangeAnnotation {
    private String label;
    private Optional<Boolean> needsConfirmation;
    private Optional<String> description;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Optional<Boolean> getNeedsConfirmation() {
        return needsConfirmation;
    }

    public void setNeedsConfirmation(Optional<Boolean> needsConfirmation) {
        this.needsConfirmation = needsConfirmation;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public void setDescription(Optional<String> description) {
        this.description = description;
    }
}
