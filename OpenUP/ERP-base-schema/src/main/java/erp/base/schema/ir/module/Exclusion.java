package erp.base.schema.ir.module;

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
	@Column
	@Description("Module")
	@Transient
	private Integer module_id;

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
	private Integer exclusion_id;

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
	@Convert(converter = StatusAttributeConverter.class)
	@Description("Status")
	@Property
	private Status state;

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

	public Integer getExclusion_id() {
		return exclusion_id;
	}

	public void setExclusion_id(Integer exclusion_id) {
		this.exclusion_id = exclusion_id;
	}

	public Status getState() {
		return state;
	}

	public void setState(Status state) {
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
