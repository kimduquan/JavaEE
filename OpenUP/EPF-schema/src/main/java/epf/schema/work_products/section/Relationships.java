/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products.section;

import epf.schema.roles.Role;
import epf.schema.tasks.Task;
import epf.schema.work_products.WorkProductSlot;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
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
public class Relationships {
    
    @ManyToMany
    @JoinTable(
            name = "ARTIFACT_FULFILLED_SLOTS",
            schema = "EPF",
            joinColumns = @JoinColumn(
                    name = "ARTIFACT"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "WORK_PRODUCT_SLOT"
            )
    )
    private List<WorkProductSlot> fulfilledSlots;
    
    @ManyToOne
    @JoinColumn(name = "RESPONSIBLE")
    private Role responsible;
    
    @ManyToMany(mappedBy = "modifies")
    private List<Role> modifiedBy;
    
    @ManyToMany(mappedBy = "outputs")
    private List<Task> outputFrom;

    public List<WorkProductSlot> getFulfilledSlots() {
        return fulfilledSlots;
    }

    public void setFulfilledSlots(List<WorkProductSlot> fulfilledSlots) {
        this.fulfilledSlots = fulfilledSlots;
    }
    
    @Name("Responsible")
    @JsonbTransient
    public Role getResponsible(){
        return responsible;
    }

    public void setResponsible(Role responsible) {
        this.responsible = responsible;
    }
    
    @Name("Modified_By")
    @JsonbTransient
    public List<Role> getModifiedBy(){
        return modifiedBy;
    }

    public void setModifiedBy(List<Role> modifiedBy) {
        this.modifiedBy = modifiedBy;
    }
    
    @Name("Input_To")
    @JsonbTransient
    public List<Task> getInputTo(){
        return null;
    }
    
    @Name("Output_From")
    @JsonbTransient
    public List<Task> getOutputFrom(){
        return outputFrom;
    }

    public void setOutputFrom(List<Task> outputFrom) {
        this.outputFrom = outputFrom;
    }
}
