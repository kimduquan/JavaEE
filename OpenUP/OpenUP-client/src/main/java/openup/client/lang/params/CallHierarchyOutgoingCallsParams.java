/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.item.CallHierarchyItem;

/**
 *
 * @author FOXCONN
 */
public class CallHierarchyOutgoingCallsParams extends WorkDoneProgressParams {
    private CallHierarchyItem item;

    public CallHierarchyItem getItem() {
        return item;
    }

    public void setItem(CallHierarchyItem item) {
        this.item = item;
    }
}
