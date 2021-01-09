/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import java.util.Optional;

/**
 *
 * @author FOXCONN
 */
public class SignatureHelp {
    private SignatureInformation[] signatures;
    private Optional<Integer> activeSignature;
    private Optional<Integer> activeParameter;

    public SignatureInformation[] getSignatures() {
        return signatures;
    }

    public void setSignatures(SignatureInformation[] signatures) {
        this.signatures = signatures;
    }

    public Optional<Integer> getActiveSignature() {
        return activeSignature;
    }

    public void setActiveSignature(Optional<Integer> activeSignature) {
        this.activeSignature = activeSignature;
    }

    public Optional<Integer> getActiveParameter() {
        return activeParameter;
    }

    public void setActiveParameter(Optional<Integer> activeParameter) {
        this.activeParameter = activeParameter;
    }
}
