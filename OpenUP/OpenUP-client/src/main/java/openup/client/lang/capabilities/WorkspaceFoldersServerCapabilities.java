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
public class WorkspaceFoldersServerCapabilities {
    private Optional<Boolean> supported;
    private Optional<String> changeNotifications;

    public Optional<Boolean> getSupported() {
        return supported;
    }

    public void setSupported(Optional<Boolean> supported) {
        this.supported = supported;
    }

    public Optional<String> getChangeNotifications() {
        return changeNotifications;
    }

    public void setChangeNotifications(Optional<String> changeNotifications) {
        this.changeNotifications = changeNotifications;
    }
}
