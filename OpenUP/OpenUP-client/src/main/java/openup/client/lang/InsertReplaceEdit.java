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
public class InsertReplaceEdit {
    private String newText;
    private Range insert;
    private Range replace;

    public String getNewText() {
        return newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    public Range getInsert() {
        return insert;
    }

    public void setInsert(Range insert) {
        this.insert = insert;
    }

    public Range getReplace() {
        return replace;
    }

    public void setReplace(Range replace) {
        this.replace = replace;
    }
}
