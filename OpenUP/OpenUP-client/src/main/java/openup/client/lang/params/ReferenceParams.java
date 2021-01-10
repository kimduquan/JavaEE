/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang.params;

import openup.client.lang.context.ReferenceContext;

/**
 *
 * @author FOXCONN
 */
public class ReferenceParams extends WorkDoneProgressParams{
    private ReferenceContext context;

    public ReferenceContext getContext() {
        return context;
    }

    public void setContext(ReferenceContext context) {
        this.context = context;
    }
}
