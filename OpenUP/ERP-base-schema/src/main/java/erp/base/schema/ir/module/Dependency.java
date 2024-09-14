package erp.base.schema.ir.module;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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
	public enum Status {
		/**
		 * 
		 */
		uninstallable,
	    /**
	     * 
	     */
	    uninstalled,
	    /**
	     * 
	     */
	    installed,
	    /**
	     * 
	     */
	    to_upgrade,
	    /**
	     * 
	     */
	    to_remove,
	    /**
	     * 
	     */
	    to_install,
	    /**
	     * 
	     */
	    unknown
	}
	
	/**
	 * 
	 */
	public class StatusAttributeConverter extends EnumAttributeConverter<Status> {

		/**
		 * 
		 */
		public StatusAttributeConverter() {
			super(Status.class, null, "_", " ");
		}
	}
	
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
	@Column(insertable = false, updatable = false)
	@Description("Module")
	@Transient
	private Integer module_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Module.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	@Relationship(type = "MODULE")
	private Module module;
	
	/**
	 * 
	 */
	@Column(insertable = false, updatable = false)
	@Description("Dependency")
	@Transient
	private Integer depend_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = Module.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "depend_id")
	@Relationship(type = "DEPENDENCY")
	private Module depend;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Convert(converter = StatusAttributeConverter.class)
	@Description("Status")
	@Property
	private Status state;
	
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

	public Integer getModule_id() {
		return module_id;
	}

	public void setModule_id(Integer module_id) {
		this.module_id = module_id;
	}

	public Integer getDepend_id() {
		return depend_id;
	}

	public void setDepend_id(Integer depend_id) {
		this.depend_id = depend_id;
	}

	public Status getState() {
		return state;
	}

	public void setState(Status state) {
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
