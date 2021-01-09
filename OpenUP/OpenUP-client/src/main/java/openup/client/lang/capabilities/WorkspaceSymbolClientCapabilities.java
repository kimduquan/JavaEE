/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.capabilities;

import java.util.Optional;
import openup.client.lang.SymbolKind;
import openup.client.lang.SymbolTag;
import openup.client.lang.util.ValueSet;

/**
 *
 * @author FOXCONN
 */
public class WorkspaceSymbolClientCapabilities {

    public Optional<Boolean> getDynamicRegistration() {
        return dynamicRegistration;
    }

    public void setDynamicRegistration(Optional<Boolean> dynamicRegistration) {
        this.dynamicRegistration = dynamicRegistration;
    }

    public Optional<ValueSet<SymbolKind>> getSymbolKind() {
        return symbolKind;
    }

    public void setSymbolKind(Optional<ValueSet<SymbolKind>> symbolKind) {
        this.symbolKind = symbolKind;
    }

    public Optional<ValueSet<SymbolTag>> getTagSupport() {
        return tagSupport;
    }

    public void setTagSupport(Optional<ValueSet<SymbolTag>> tagSupport) {
        this.tagSupport = tagSupport;
    }
    
    private Optional<Boolean> dynamicRegistration;
    private Optional<ValueSet<SymbolKind>> symbolKind;
    private Optional<ValueSet<SymbolTag>> tagSupport;
}
