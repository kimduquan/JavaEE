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
public class SignatureInformation {
    private String label;
    private Optional<MarkupContent> documentation;
    private Optional<ParameterInformation[]> parameters;
    private Optional<Integer> activeParameter;

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

    public Optional<ParameterInformation[]> getParameters() {
        return parameters;
    }

    public void setParameters(Optional<ParameterInformation[]> parameters) {
        this.parameters = parameters;
    }

    public Optional<Integer> getActiveParameter() {
        return activeParameter;
    }

    public void setActiveParameter(Optional<Integer> activeParameter) {
        this.activeParameter = activeParameter;
    }
}
