/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.micro.work_products;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import openup.micro.roles.Role;
import openup.micro.tasks.Task;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;

/**
 *
 * @author FOXCONN
 */
@Type
@Entity
@Table(name = "Artifacts")
public class Artifact implements Serializable {

    @Id
    @Column
    private String id;
    
    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
