package erp.base.schema.ir.module.module;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.module.Module;
import erp.base.schema.ir.module.State;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "ir_module_module_exclusion")
@Description("Module exclusion")
public class Exclusion {
	
	public class StatusAttributeConverter extends EnumAttributeConverter<State> {
		public StatusAttributeConverter() {
			super(State.class, null, "_", " ");
		}
	}
	
	@Id
	private int id;

	@Column
	private String name;
	
	@Transient
	private Integer module_id;

	@ManyToOne(targetEntity = Module.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	@Description("Module")
	private Module module;
	
	@Transient
	@Description("Exclusion Module")
	private Integer exclusion_id;
	
	@Transient
	@Description("Status")
	private Dep_State state;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Integer getExclusion_id() {
		return exclusion_id;
	}

	public void setExclusion_id(Integer exclusion_id) {
		this.exclusion_id = exclusion_id;
	}

	public Dep_State getState() {
		return state;
	}

	public void setState(Dep_State state) {
		this.state = state;
	}
}
