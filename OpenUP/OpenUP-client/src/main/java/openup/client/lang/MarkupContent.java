/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.kind.MarkupKind;

/**
 *
 * @author FOXCONN
 */
public class MarkupContent {
    private MarkupKind kind;
    private String value;

    public MarkupKind getKind() {
        return kind;
    }

    public void setKind(MarkupKind kind) {
        this.kind = kind;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
