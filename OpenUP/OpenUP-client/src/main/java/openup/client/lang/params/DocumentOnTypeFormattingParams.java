/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.options.FormattingOptions;

/**
 *
 * @author FOXCONN
 */
public class DocumentOnTypeFormattingParams extends TextDocumentPositionParams {
    private String ch;
    private FormattingOptions options;

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public FormattingOptions getOptions() {
        return options;
    }

    public void setOptions(FormattingOptions options) {
        this.options = options;
    }
}
