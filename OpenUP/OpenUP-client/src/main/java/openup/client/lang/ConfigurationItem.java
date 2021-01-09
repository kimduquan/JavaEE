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
public class ConfigurationItem {
    private Optional<DocumentUri> scopeUri;
    private Optional<String> section;

    public Optional<DocumentUri> getScopeUri() {
        return scopeUri;
    }

    public void setScopeUri(Optional<DocumentUri> scopeUri) {
        this.scopeUri = scopeUri;
    }

    public Optional<String> getSection() {
        return section;
    }

    public void setSection(Optional<String> section) {
        this.section = section;
    }
}
