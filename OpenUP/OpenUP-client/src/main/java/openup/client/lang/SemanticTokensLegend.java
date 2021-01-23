/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

/**
 *
 * @author FOXCONN
 */
public class SemanticTokensLegend {
    private String[] tokenTypes;
    private String[] tokenModifiers;

    public String[] getTokenTypes() {
        return tokenTypes;
    }

    public void setTokenTypes(String[] tokenTypes) {
        this.tokenTypes = tokenTypes;
    }

    public String[] getTokenModifiers() {
        return tokenModifiers;
    }

    public void setTokenModifiers(String[] tokenModifiers) {
        this.tokenModifiers = tokenModifiers;
    }
}
