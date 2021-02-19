/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.Role)
@Schema(name = OpenUP.Role, title = "Role")
@Entity(name = OpenUP.Role)
@Table(schema = OpenUP.Schema, name = "OPENUP_ROLE", indexes = {@Index(columnList = "NAME")})
public class Role {

    @Id
    private String name;
    
    @ManyToMany
    @JoinTable(
    		name = "OPENUP_ROLES",
    		schema = OpenUP.Schema,
    		joinColumns = {@JoinColumn(name = "NAME")},
    		inverseJoinColumns = {@JoinColumn(name = "ROLE")},
    		indexes = {@Index(columnList = "NAME")}
    		)
    private List<epf.schema.roles.Role> roles;
    
    @Column(name = "SUMMARY")
    private String summary;
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<epf.schema.roles.Role> getRoles() {
		return roles;
	}

	public void setRoles(List<epf.schema.roles.Role> roles) {
		this.roles = roles;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
}
