/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.epf.work_products;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import openup.epf.roles.Role;
import openup.epf.tasks.Task;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Artifact",
        title = "Artifact"
)
@Entity
@Table(name = "ARTIFACT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@JsonbPropertyOrder({
    "name",
    "fulfilledSlots"
})
public class Artifact implements Serializable {
    
    @Column(name = "NAME")
    @Id
    private String name;
    
    @ManyToMany
    @JoinTable(
            name = "ARTIFACT_FULFILLED_SLOTS",
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
