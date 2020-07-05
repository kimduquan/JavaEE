/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.work_products;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import openup.service.roles.Role;
import openup.service.tasks.Task;
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
@Table(name = "Artifact")
public class Artifact implements Serializable {
    
    @Column
    @Id
    private String name;
    
    @ManyToMany
    @JoinTable(
            name = "FulfilledSlots",
            joinColumns = @JoinColumn(
                    name = "Artifact"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "WorkProductSlot"
            )
    )
    private List<WorkProductSlot> fulfilledSlots;

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
    public Role[] getResponsible(){
        return null;
    }
    
    @Name("Modified_By")
    public Role[] getModifiedBy(){
        return null;
    }
    
    @Name("Input_To")
    public Task[] getInputTo(){
        return null;
    }
    
    @Name("Output_From")
    public Task[] getOutputFrom(){
        return null;
    }
}
