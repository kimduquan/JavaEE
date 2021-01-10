/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.PrepareSupportDefaultBehavior;

/**
 *
 * @author FOXCONN
 */
public class RenameClientCapabilities {
    private Optional<Boolean> dynamicRegistration;
    private Optional<Boolean> prepareSupport;
    private Optional<PrepareSupportDefaultBehavior> prepareSupportDefaultBehavior;
    private Optional<Boolean> honorsChangeAnnotations;

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Optional<Boolean> getPrepareSupport() {
        return prepareSupport;
    }

    public void setPrepareSupport(Optional<Boolean> prepareSupport) {
        this.prepareSupport = prepareSupport;
    }

    public Optional<PrepareSupportDefaultBehavior> getPrepareSupportDefaultBehavior() {
        return prepareSupportDefaultBehavior;
    }

    public void setPrepareSupportDefaultBehavior(Optional<PrepareSupportDefaultBehavior> prepareSupportDefaultBehavior) {
        this.prepareSupportDefaultBehavior = prepareSupportDefaultBehavior;
    }

    public Optional<Boolean> getHonorsChangeAnnotations() {
        return honorsChangeAnnotations;
    }

    public void setHonorsChangeAnnotations(Optional<Boolean> honorsChangeAnnotations) {
        this.honorsChangeAnnotations = honorsChangeAnnotations;
    }
}
