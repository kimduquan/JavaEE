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
public class MonikerClientCapabilities {
    private Optional<Boolean> dynamicRegistration;

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }
}
