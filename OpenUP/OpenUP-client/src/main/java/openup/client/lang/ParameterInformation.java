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
public class ParameterInformation {
    private String label;
    private Optional<MarkupContent> documentation;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Optional<MarkupContent> getDocumentation() {
        return documentation;
    }

    public void setDocumentation(Optional<MarkupContent> documentation) {
        this.documentation = documentation;
    }
}
