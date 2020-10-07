/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products.section;

import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Tailoring {
    
    @Column(name = "IMPACT_OF_NOT_HAVING")
    private JsonObject impactOfNotHaving;
    
    @Column(name = "REASONS_FOR_NOT_NEEDING")
    private JsonObject reasonsForNotNeeding;
    
    @Column(name = "REPRESENTATION_OPTIONS")
    private JsonObject representationOptions;

    public JsonObject getImpactOfNotHaving() {
        return impactOfNotHaving;
    }

    public void setImpactOfNotHaving(JsonObject impactOfNotHaving) {
        this.impactOfNotHaving = impactOfNotHaving;
    }

    public JsonObject getReasonsForNotNeeding() {
        return reasonsForNotNeeding;
    }

    public void setReasonsForNotNeeding(JsonObject reasonsForNotNeeding) {
        this.reasonsForNotNeeding = reasonsForNotNeeding;
    }

    public JsonObject getRepresentationOptions() {
        return representationOptions;
    }

    public void setRepresentationOptions(JsonObject representationOptions) {
        this.representationOptions = representationOptions;
    }
}
