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
public class CallHierarchyOutgoingCall {
    private CallHierarchyItem to;
    private Range[] fromRanges;

    public CallHierarchyItem getTo() {
        return to;
    }

    public void setTo(CallHierarchyItem to) {
        this.to = to;
    }

    public Range[] getFromRanges() {
        return fromRanges;
    }

    public void setFromRanges(Range[] fromRanges) {
        this.fromRanges = fromRanges;
    }
}
