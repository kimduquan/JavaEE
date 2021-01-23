/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.kind.CodeActionKind;
import openup.client.lang.util.Properties;
import openup.client.lang.util.ValueSet;

/**
 *
 * @author FOXCONN
 */
public class CodeActionClientCapabilities {

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Optional<CodeActionLiteralSupport> getCodeActionLiteralSupport() {
        return codeActionLiteralSupport;
    }

    public void setCodeActionLiteralSupport(Optional<CodeActionLiteralSupport> codeActionLiteralSupport) {
        this.codeActionLiteralSupport = codeActionLiteralSupport;
    }

    public Optional<Boolean> getIsPreferredSupport() {
        return isPreferredSupport;
    }

    public void setIsPreferredSupport(Optional<Boolean> isPreferredSupport) {
        this.isPreferredSupport = isPreferredSupport;
    }

    public Optional<Boolean> getDisabledSupport() {
        return disabledSupport;
    }

    public void setDisabledSupport(Optional<Boolean> disabledSupport) {
        this.disabledSupport = disabledSupport;
    }

    public Optional<Boolean> getDataSupport() {
        return dataSupport;
    }

    public void setDataSupport(Optional<Boolean> dataSupport) {
        this.dataSupport = dataSupport;
    }

    public Optional<Properties> getResolveSupport() {
        return resolveSupport;
    }

    public void setResolveSupport(Optional<Properties> resolveSupport) {
        this.resolveSupport = resolveSupport;
    }

    public Optional<Boolean> getHonorsChangeAnnotations() {
        return honorsChangeAnnotations;
    }

    public void setHonorsChangeAnnotations(Optional<Boolean> honorsChangeAnnotations) {
        this.honorsChangeAnnotations = honorsChangeAnnotations;
    }
    public class CodeActionLiteralSupport {
        private ValueSet<CodeActionKind> codeActionKind;

        public ValueSet<CodeActionKind> getCodeActionKind() {
            return codeActionKind;
        }

        public void setCodeActionKind(ValueSet<CodeActionKind> codeActionKind) {
            this.codeActionKind = codeActionKind;
        }
    }
    private Optional<Boolean> dynamicRegistration;
    private Optional<CodeActionLiteralSupport> codeActionLiteralSupport;
    private Optional<Boolean> isPreferredSupport;
    private Optional<Boolean> disabledSupport;
    private Optional<Boolean> dataSupport;
    private Optional<Properties> resolveSupport;
    private Optional<Boolean> honorsChangeAnnotations;
}
