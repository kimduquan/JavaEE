/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.roles.section;

import epf.schema.tasks.Task;
import epf.schema.work_products.Artifact;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.eclipse.microprofile.graphql.Name;

/**
 *
 * @author FOXCONN
 */
@Embeddable
public class Relationships {
    
    @ManyToMany
    @JoinTable(
            name = "ROLE_ADDITIONALLY_PERFORMS",
            schema = "EPF",
            joinColumns = @JoinColumn(
                    name = "ROLE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "TASK"
            )
    )
    private List<Task> additionallyPerforms;
    
    @ManyToMany
    @JoinTable(
            name = "ROLE_MODIFIES",
            schema = "EPF",
            joinColumns = @JoinColumn(
                    name = "ROLE"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> modifies;
    
    @Name("Additionally_Performs")
    public List<Task> getAdditionallyPerforms(){
        return additionallyPerforms;
    }

    public void setAdditionallyPerforms(List<Task> additionallyPerforms) {
        this.additionallyPerforms = additionallyPerforms;
    }
    
    @Name("Modifies")
    public List<Artifact> getModifies(){
        return modifies;
    }

    public void setModifies(List<Artifact> modifies) {
        this.modifies = modifies;
    }
}
