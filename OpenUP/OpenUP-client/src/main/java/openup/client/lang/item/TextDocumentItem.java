/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.item;

import openup.client.lang.DocumentUri;

/**
 *
 * @author FOXCONN
 */
public class TextDocumentItem {
    private DocumentUri uri;
    private String languageId;
    private Integer version;
    private String text;

    public DocumentUri getUri() {
        return uri;
    }

    public void setUri(DocumentUri uri) {
        this.uri = uri;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
