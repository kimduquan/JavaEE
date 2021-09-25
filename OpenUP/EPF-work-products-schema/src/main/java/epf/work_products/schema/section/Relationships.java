/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.work_products.schema.section;

import epf.work_products.schema.WorkProductSlot;
import epf.work_products.schema.WorkProducts;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;

/**
 *
 * @author FOXCONN
 */
@Type
@Embeddable
public class Relationships implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
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
    private List<WorkProductSlot> fulfilledSlots;
    
    /**
     * 
     */
    @Column(name = "RESPONSIBLE")
    private String responsible;
    
    /**
     * 
     */
    @ElementCollection
    @CollectionTable(
    		name = "ROLE_MODIFIES",
    		schema = WorkProducts.SCHEMA,
    		joinColumns = @JoinColumn(name = "ARTIFACT"),
    		indexes = {@Index(columnList = "ARTIFACT")})
    @Column(name = "ROLE")
    private List<String> modifiedBy;

    public List<WorkProductSlot> getFulfilledSlots() {
        return fulfilledSlots;
    }

    public void setFulfilledSlots(final List<WorkProductSlot> fulfilledSlots) {
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
    public List<String> getModifiedBy(){
        return modifiedBy;
    }

    public void setModifiedBy(final List<String> modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
