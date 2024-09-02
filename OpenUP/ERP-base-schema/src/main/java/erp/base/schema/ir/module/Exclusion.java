package erp.base.schema.ir.module;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_module_module_exclusion")
@Description("Module exclusion")
@NodeEntity("Module Exclusion")
public class Exclusion {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column
	@Description("Module")
	@Transient
	private String module_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Module.class)
	@JoinColumn(name = "module_id")
	@Relationship(type = "MODULE")
	private Module module;
	
	/**
	 * 
	 */
	@Column
	@Description("Exclusion Module")
	@Transient
	private String exclusion_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Module.class)
	@JoinColumn(name = "exclusion_id")
	@Relationship(type = "EXCLUSION_MODULE")
	private Module exclusion;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Status")
	@Property
	private String state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule_id() {
		return module_id;
	}

	public void setModule_id(String module_id) {
		this.module_id = module_id;
	}

	public String getExclusion_id() {
		return exclusion_id;
	}

	public void setExclusion_id(String exclusion_id) {
		this.exclusion_id = exclusion_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Module getExclusion() {
		return exclusion;
	}

	public void setExclusion(Module exclusion) {
		this.exclusion = exclusion;
	}
}
