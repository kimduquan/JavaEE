/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf;

import epf.schema.work_products.Artifact;
import epf.schema.work_products.Domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type
@Schema(title = "Artifacts")
@Entity
@Table(name = "ARTIFACTS")
public class Artifacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "ARTIFACT")
    private Artifact artifact;
    
    @Column(name = "NAME", nullable = false)
    private String name;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    @ManyToOne
    @JoinColumn(name = "DOMAINS")
    private Domain domains;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setArtifact(Artifact artifact) {
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

    public Domain getDomains() {
        return domains;
    }

    public void setDomains(Domain domains) {
        this.domains = domains;
    }
}
