/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.MarkupKind;

/**
 *
 * @author FOXCONN
 */
public class SignatureHelpClientCapabilities {

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Optional<SignatureInformation> getSignatureInformation() {
        return signatureInformation;
    }

    public void setSignatureInformation(Optional<SignatureInformation> signatureInformation) {
        this.signatureInformation = signatureInformation;
    }

    public Optional<Boolean> getContextSupport() {
        return contextSupport;
    }

    public void setContextSupport(Optional<Boolean> contextSupport) {
        this.contextSupport = contextSupport;
    }
    public class SignatureInformation {
        private Optional<MarkupKind[]> documentationFormat;
        private Optional<ParameterInformation> parameterInformation;
        private Optional<Boolean> activeParameterSupport;

        public Optional<MarkupKind[]> getDocumentationFormat() {
            return documentationFormat;
        }

        public void setDocumentationFormat(Optional<MarkupKind[]> documentationFormat) {
            this.documentationFormat = documentationFormat;
        }

        public Optional<ParameterInformation> getParameterInformation() {
            return parameterInformation;
        }

        public void setParameterInformation(Optional<ParameterInformation> parameterInformation) {
            this.parameterInformation = parameterInformation;
        }

        public Optional<Boolean> getActiveParameterSupport() {
            return activeParameterSupport;
        }

        public void setActiveParameterSupport(Optional<Boolean> activeParameterSupport) {
            this.activeParameterSupport = activeParameterSupport;
        }
    }
    public class ParameterInformation {
        private Optional<Boolean> labelOffsetSupport;

        public Optional<Boolean> getLabelOffsetSupport() {
            return labelOffsetSupport;
        }

        public void setLabelOffsetSupport(Optional<Boolean> labelOffsetSupport) {
            this.labelOffsetSupport = labelOffsetSupport;
        }
    }
    private Optional<Boolean> dynamicRegistration;
    private Optional<SignatureInformation> signatureInformation;
    private Optional<Boolean> contextSupport;
}
