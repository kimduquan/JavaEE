package erp.base.schema.ir.module;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
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
	private Integer depend_id;
	
	@ManyToOne(targetEntity = Module.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "depend_id")
	@Description("Dependency")
	private Module depend;
	
	@Column
	@Description("Status")
	private Dep_State state;
	
	@Column
	@DefaultValue("true")
	private Boolean auto_install_required = true;
}
