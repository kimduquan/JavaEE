/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import openup.client.lang.item.CallHierarchyItem;

/**
 *
 * @author FOXCONN
 */
public class CallHierarchyIncomingCall {
    private CallHierarchyItem from;
    private Range[] fromRanges;

    public CallHierarchyItem getFrom() {
        return from;
    }

    public void setFrom(CallHierarchyItem from) {
        this.from = from;
    }

    public Range[] getFromRanges() {
        return fromRanges;
    }

    public void setFromRanges(Range[] fromRanges) {
        this.fromRanges = fromRanges;
    }
}
