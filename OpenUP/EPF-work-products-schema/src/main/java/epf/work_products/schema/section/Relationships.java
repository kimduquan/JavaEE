package epf.work_products.schema.section;

import epf.work_products.schema.WorkProductSlot;
import epf.work_products.schema.WorkProducts;
import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;

@Type
@Embeddable
public class Relationships implements Serializable {
    
    private static final long serialVersionUID = 1L;

	@ManyToMany
    @JoinTable(
            name = "ARTIFACT_FULFILLED_SLOTS",
            schema = WorkProducts.SCHEMA,
            joinColumns = @JoinColumn(
                    name = "ARTIFACT"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "WORK_PRODUCT_SLOT"
            )
    )
    private Set<WorkProductSlot> fulfilledSlots;
    
    @Column(name = "RESPONSIBLE")
    private String responsible;
    
    @ElementCollection
    @CollectionTable(
    		name = "ROLE_MODIFIES",
    		schema = WorkProducts.SCHEMA,
    		joinColumns = @JoinColumn(name = "ARTIFACT"),
    		indexes = {@Index(columnList = "ARTIFACT")})
    @Column(name = "ROLE", nullable = false)
    private Set<String> modifiedBy;

    public Set<WorkProductSlot> getFulfilledSlots() {
        return fulfilledSlots;
    }

    public void setFulfilledSlots(final Set<WorkProductSlot> fulfilledSlots) {
        this.fulfilledSlots = fulfilledSlots;
    }
    
    @Name("Responsible")
    public String getResponsible(){
        return responsible;
    }

    public void setResponsible(final String responsible) {
        this.responsible = responsible;
    }
    
    @Name("Modified_By")
    public Set<String> getModifiedBy(){
        return modifiedBy;
    }

    public void setModifiedBy(final Set<String> modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
