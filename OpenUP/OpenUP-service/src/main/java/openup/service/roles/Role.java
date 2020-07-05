/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.service.roles;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import openup.service.tasks.Task;
import openup.service.work_products.Artifact;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;

/**
 *
 * @author FOXCONN
 */
@Type
@Entity
@Table(name = "Roles")
public class Role implements Serializable {

    @Id
    @Column
    private String id;
    
    @Column
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Name("Additionally_Performs")
    public Task[] getAdditionallyPerforms(){
        return null;
    }
    
    @Name("Modifies")
    public Artifact[] getModifies(){
        return null;
    }
}
