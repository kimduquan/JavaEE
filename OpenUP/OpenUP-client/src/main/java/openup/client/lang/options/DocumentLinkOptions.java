/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.options;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class DocumentLinkOptions extends WorkDoneProgressOptions {
    private Optional<Boolean> resolveProvider;

    public Optional<Boolean> getResolveProvider() {
        return resolveProvider;
    }

    public void setResolveProvider(Optional<Boolean> resolveProvider) {
        this.resolveProvider = resolveProvider;
    }
}
