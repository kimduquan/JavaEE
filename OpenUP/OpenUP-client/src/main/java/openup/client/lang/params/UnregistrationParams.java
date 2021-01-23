/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.Unregistration;

/**
 *
 * @author FOXCONN
 */
public class UnregistrationParams {
    private Unregistration[] unregisterations;

    public Unregistration[] getUnregisterations() {
        return unregisterations;
    }

    public void setUnregisterations(Unregistration[] unregisterations) {
        this.unregisterations = unregisterations;
    }
}
