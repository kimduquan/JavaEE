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
public class MessageActionItem {
    
    private Optional<Boolean> additionalPropertiesSupport;
    private String title;

    public Optional<Boolean> getAdditionalPropertiesSupport() {
        return additionalPropertiesSupport;
    }

    public void setAdditionalPropertiesSupport(Optional<Boolean> additionalPropertiesSupport) {
        this.additionalPropertiesSupport = additionalPropertiesSupport;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
