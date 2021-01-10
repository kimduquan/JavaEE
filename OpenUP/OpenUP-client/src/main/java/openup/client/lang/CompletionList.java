/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.item.CompletionItem;

/**
 *
 * @author FOXCONN
 */
public class CompletionList {
    private Boolean isIncomplete;
    private CompletionItem[] items;

    public Boolean getIsIncomplete() {
        return isIncomplete;
    }

    public void setIsIncomplete(Boolean isIncomplete) {
        this.isIncomplete = isIncomplete;
    }

    public CompletionItem[] getItems() {
        return items;
    }

    public void setItems(CompletionItem[] items) {
        this.items = items;
    }
}
