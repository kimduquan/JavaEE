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
	
	@Description("Status")
	private Dep_State state;
}
