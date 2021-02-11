/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.Artifact)
@Schema(name = OpenUP.Artifact, title = "Artifact")
@Entity(name = OpenUP.Artifact)
@Table(schema = OpenUP.Schema, name = "OPENUP_ARTIFACT", indexes = {@Index(columnList = "DOMAINS")})
public class Artifact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ARTIFACT")
    private epf.schema.work_products.Artifact artifact;
    
    @ManyToOne
    @JoinColumn(name = "RESPONSIBLE")
    private Role responsible;
    
    @ManyToMany
    @JoinTable(
    		name = "OPENUP_ROLE_MODIFIES",
    		schema = OpenUP.Schema,
    		joinColumns = {@JoinColumn(name = "ID")},
    		inverseJoinColumns = {@JoinColumn(name = "NAME")},
    		indexes = {@Index(columnList = "NAME")}
    		)
    private List<Role> modifiedBy;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public epf.schema.work_products.Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(epf.schema.work_products.Artifact artifact) {
        this.artifact = artifact;
    }

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
}
