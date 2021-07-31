/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products.section;

import epf.schema.EPF;
import epf.schema.roles.Role;
import epf.schema.work_products.WorkProductSlot;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
            schema = EPF.SCHEMA,
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
    @ManyToOne
    @JoinColumn(name = "RESPONSIBLE")
    private Role responsible;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "ROLE_MODIFIES",
            schema = EPF.SCHEMA,
            joinColumns = @JoinColumn(
                    name = "ARTIFACT"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ROLE"
            )
    )
    private List<Role> modifiedBy;

    public List<WorkProductSlot> getFulfilledSlots() {
        return fulfilledSlots;
    }

    public void setFulfilledSlots(final List<WorkProductSlot> fulfilledSlots) {
        this.fulfilledSlots = fulfilledSlots;
    }
    
    @Name("Responsible")
    public Role getResponsible(){
        return responsible;
    }

    public void setResponsible(final Role responsible) {
        this.responsible = responsible;
    }
    
    @Name("Modified_By")
    public List<Role> getModifiedBy(){
        return modifiedBy;
    }

    public void setModifiedBy(final List<Role> modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
}
