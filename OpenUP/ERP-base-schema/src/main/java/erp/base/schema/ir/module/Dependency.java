package erp.base.schema.ir.module;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "ir_module_module_dependency")
@Description("Module dependency")
public class Dependency {

	/**
	 * 
	 */
	@Column
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Module.class)
	@Description("Module")
	private String module_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Module.class)
	@Description("Dependency")
	private String depend_id;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Description("Status")
	private String state;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("true")
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
}
