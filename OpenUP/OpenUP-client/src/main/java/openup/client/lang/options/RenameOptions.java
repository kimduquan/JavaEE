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
public class RenameOptions extends WorkDoneProgressOptions {
    private Optional<Boolean> prepareProvider;

    public Optional<Boolean> getPrepareProvider() {
        return prepareProvider;
    }

    public void setPrepareProvider(Optional<Boolean> prepareProvider) {
        this.prepareProvider = prepareProvider;
    }
}
