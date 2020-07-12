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
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(
        name = "Domain",
        title = "Domain"
)
@Entity
@Table(name = "_Domain")
public class Domain implements Serializable {
    
    @Column
    @Id
    private String name;
    
    @ManyToMany
    @JoinTable(
            name = "Work_Products",
            joinColumns = @JoinColumn(
                    name = "Domain"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "Artifact"
            )
    )
    private List<Artifact> workProducts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Name("Work_Products")
    public List<Artifact> getWorkProducts(){
        return workProducts;
    }

    public void setWorkProducts(List<Artifact> workProducts) {
        this.workProducts = workProducts;
    }
}
