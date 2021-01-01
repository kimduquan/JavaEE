/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schema.work_products;

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
import epf.schema.EPF;

/**
 *
 * @author FOXCONN
 */
@Type(EPF.Domain)
@Schema(name = EPF.Domain, title = "Domain")
@Entity(name = EPF.Domain)
@Table(schema = EPF.Schema, name = "_DOMAIN")
@JsonbPropertyOrder({
    "name",
    "workProducts"
})
@NamedQuery(
        name = Domain.DOMAINS, 
        query = "SELECT d FROM EPF_Domain AS d")
public class Domain {

    @Column(name = "NAME")
    @Id
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @ManyToMany
    @JoinTable(
            name = "WORK_PRODUCTS",
            schema = EPF.Schema,
            joinColumns = @JoinColumn(
                    name = "DOMAIN"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "ARTIFACT"
            )
    )
    private List<Artifact> workProducts;
    
    public static final String DOMAINS = "EPF_Domain.Domains";

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
