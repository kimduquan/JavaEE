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
public class HoverClientCapabilities {
    private Optional<Boolean> dynamicRegistration;
    private Optional<MarkupKind[]> contentFormat;

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Optional<MarkupKind[]> getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(Optional<MarkupKind[]> contentFormat) {
        this.contentFormat = contentFormat;
    }
}
