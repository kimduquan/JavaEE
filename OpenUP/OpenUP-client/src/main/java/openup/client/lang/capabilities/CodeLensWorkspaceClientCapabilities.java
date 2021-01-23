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
public class CodeLensWorkspaceClientCapabilities {
    private Optional<Boolean> refreshSupport;

    public Optional<Boolean> getRefreshSupport() {
        return refreshSupport;
    }

    public void setRefreshSupport(Optional<Boolean> refreshSupport) {
        this.refreshSupport = refreshSupport;
    }
}
