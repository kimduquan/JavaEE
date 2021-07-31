/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.schema.roles;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.eclipse.microprofile.graphql.Type;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import openup.schema.OpenUP;

/**
 *
 * @author FOXCONN
 */
@Type(OpenUP.ROLE)
@Schema(name = OpenUP.ROLE, title = "Role")
@Entity(name = OpenUP.ROLE)
@Table(schema = OpenUP.SCHEMA, name = "OPENUP_ROLE", indexes = {@Index(columnList = "NAME")})
public class Role implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    @Id
    private String name;
    
    /**
     * 
     */
    @ManyToMany
    @JoinTable(
            name = "OPENUP_ROLE_SET",
            schema = OpenUP.SCHEMA,
            joinColumns = @JoinColumn(name = "NAME"),
            inverseJoinColumns = @JoinColumn(name = "ROLE"),
            indexes = {@Index(columnList = "NAME")}
    )
    private List<epf.schema.roles.Role> roles;
    
    /**
     * 
     */
    @ElementCollection
    private Map<String, String> claims = new ConcurrentHashMap<>();
    
    /**
     * 
     */
    @Column(name = "SUMMARY")
    private String summary;
    
    public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	public Map<String, String> getClaims() {
		return claims;
	}

	public void setClaims(final Map<String, String> claims) {
		this.claims = claims;
	}

	public List<epf.schema.roles.Role> getRoles() {
		return roles;
	}

	public void setRoles(final List<epf.schema.roles.Role> roles) {
		this.roles = roles;
	}
}
