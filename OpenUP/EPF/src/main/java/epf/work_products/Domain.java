/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.work_products;

import java.util.List;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
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
@Table(name = "_DOMAIN", schema = "EPF")
@NamedQuery(name = "Domain.Domains", query = "SELECT d FROM Domain AS d")
@JsonbPropertyOrder({
    "name",
    "workProducts"
})
public class Domain {

    @Column(name = "NAME")
    @Id
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @ManyToMany
    @JoinTable(
            name = "WORK_PRODUCTS",
            schema = "EPF",
            joinColumns = @JoinColumn(
                    name = "DOMAIN"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> workProducts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    @Name("Work_Products")
    public List<Artifact> getWorkProducts(){
        return workProducts;
    }

    public void setWorkProducts(List<Artifact> workProducts) {
        this.workProducts = workProducts;
    }
}
