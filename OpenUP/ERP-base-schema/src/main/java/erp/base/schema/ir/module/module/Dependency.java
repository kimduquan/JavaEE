package erp.base.schema.ir.module.module;

import org.eclipse.microprofile.graphql.DefaultValue;
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
@Table(name = "ir_module_module_dependency")
@Description("Module dependency")
public class Dependency {
	
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
	@Description("Dependency")
	private Integer depend_id;
	
	@Transient
	@Description("Status")
	private Dep_State state;
	
	@Column
	@DefaultValue("true")
	private Boolean auto_install_required = true;

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

	public Integer getDepend_id() {
		return depend_id;
	}

	public void setDepend_id(Integer depend_id) {
		this.depend_id = depend_id;
	}

	public Dep_State getState() {
		return state;
	}

	public void setState(Dep_State state) {
		this.state = state;
	}

	public Boolean getAuto_install_required() {
		return auto_install_required;
	}

	public void setAuto_install_required(Boolean auto_install_required) {
		this.auto_install_required = auto_install_required;
	}
}
