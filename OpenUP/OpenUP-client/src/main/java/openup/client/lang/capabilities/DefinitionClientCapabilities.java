/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class DefinitionClientCapabilities {
    private Optional<Boolean> dynamicRegistration;
    private Optional<Boolean> linkSupport;

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Optional<Boolean> getLinkSupport() {
        return linkSupport;
    }

    public void setLinkSupport(Optional<Boolean> linkSupport) {
        this.linkSupport = linkSupport;
    }
}
