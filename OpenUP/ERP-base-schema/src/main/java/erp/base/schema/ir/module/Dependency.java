package erp.base.schema.ir.module;

import org.eclipse.microprofile.graphql.DefaultValue;
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
@Table(name = "ir_module_module_dependency")
@Description("Module dependency")
@NodeEntity("Module Dependency")
public class Dependency {
	
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
	@Description("Dependency")
	@Transient
	private String depend_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Module.class)
	@JoinColumn(name = "depend_id")
	@Relationship(type = "DEPENDENCY")
	private Module depend;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Status")
	@Property
	private String state;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
	@Property
	private Boolean auto_install_required = true;

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

	public String getDepend_id() {
		return depend_id;
	}

	public void setDepend_id(String depend_id) {
		this.depend_id = depend_id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Boolean getAuto_install_required() {
		return auto_install_required;
	}

	public void setAuto_install_required(Boolean auto_install_required) {
		this.auto_install_required = auto_install_required;
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

	public Module getDepend() {
		return depend;
	}

	public void setDepend(Module depend) {
		this.depend = depend;
	}
}
