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
public class FoldingRangeClientCapabilities {
    private Optional<Boolean> dynamicRegistration;
    private Optional<Integer> rangeLimit;
    private Optional<Boolean> lineFoldingOnly;

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Optional<Integer> getRangeLimit() {
        return rangeLimit;
    }

    public void setRangeLimit(Optional<Integer> rangeLimit) {
        this.rangeLimit = rangeLimit;
    }

    public Optional<Boolean> getLineFoldingOnly() {
        return lineFoldingOnly;
    }

    public void setLineFoldingOnly(Optional<Boolean> lineFoldingOnly) {
        this.lineFoldingOnly = lineFoldingOnly;
    }
}
