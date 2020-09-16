/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.roles.section;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class MoreInformation {
    
    @Column(name = "GUIDELINES")
    private JsonObject guidelines;

    public JsonObject getGuidelines() {
        return guidelines;
    }

    public void setGuidelines(JsonObject guidelines) {
        this.guidelines = guidelines;
    }
}
