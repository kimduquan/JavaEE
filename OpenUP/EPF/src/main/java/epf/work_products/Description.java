/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.work_products;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Description {
    
    @Column(name = "MAIN_DESCRIPTION")
    private JsonObject mainDescription;
    
    @Column(name = "BRIEF_OUTLINE")
    private JsonObject briefOutline;

    public JsonObject getMainDescription() {
        return mainDescription;
    }

    public void setMainDescription(JsonObject mainDescription) {
        this.mainDescription = mainDescription;
    }

    public JsonObject getBriefOutline() {
        return briefOutline;
    }

    public void setBriefOutline(JsonObject briefOutline) {
        this.briefOutline = briefOutline;
    }
}
