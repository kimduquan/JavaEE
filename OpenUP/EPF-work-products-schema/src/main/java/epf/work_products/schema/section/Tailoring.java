package epf.work_products.schema.section;

import java.io.Serializable;
import jakarta.json.JsonObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.eclipse.microprofile.graphql.Type;

@Type
@Embeddable
public class Tailoring implements Serializable {
    
    private static final long serialVersionUID = 1L;

	@Column(name = "IMPACT_OF_NOT_HAVING")
    private JsonObject impactOfNotHaving;
    
    @Column(name = "REASONS_FOR_NOT_NEEDING")
    private JsonObject reasonsForNotNeeding;
    
    @Column(name = "REPRESENTATION_OPTIONS")
    private JsonObject representationOptions;

    public JsonObject getImpactOfNotHaving() {
        return impactOfNotHaving;
    }

    public void setImpactOfNotHaving(final JsonObject impactOfNotHaving) {
        this.impactOfNotHaving = impactOfNotHaving;
    }

    public JsonObject getReasonsForNotNeeding() {
        return reasonsForNotNeeding;
    }

    public void setReasonsForNotNeeding(final JsonObject reasonsForNotNeeding) {
        this.reasonsForNotNeeding = reasonsForNotNeeding;
    }

    public JsonObject getRepresentationOptions() {
        return representationOptions;
    }

    public void setRepresentationOptions(final JsonObject representationOptions) {
        this.representationOptions = representationOptions;
    }
}
