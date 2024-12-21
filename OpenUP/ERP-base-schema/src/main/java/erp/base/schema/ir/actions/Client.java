package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_act_client")
@Description("Client Action")
public class Client extends Actions {
	
	public enum TargetWindow {
		@Description("Current Window")
		current,
		@Description("New Window")
		new_,
		@Description("Full Screen")
		fullscreen,
		@Description("Main action of Current Window")
		main
	}
	
	public class TargetWindowAttributeConverter extends EnumAttributeConverter<TargetWindow> {
		public TargetWindowAttributeConverter() {
			super(TargetWindow.class, "_", null, null);
		}
	}

	@Column
	@DefaultValue("ir.actions.client")
	private String type = "ir.actions.client";
	
	@Column(nullable = false)
	@NotNull
	@Description("Client action tag")
	private String tag;
	
	@Column
	@Enumerated(EnumType.STRING)
	@Convert(converter = TargetWindowAttributeConverter.class)
	@DefaultValue("current")
	@Description("Target Window")
	private TargetWindow target = TargetWindow.current;
	
	@Column
	@Description("Destination Model")
	private String res_model;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("{}")
	@Description("Context Value")
	private String context = "{}";
	
	@Transient
	@Description("Supplementary arguments")
	private byte[] params;
	
	@Column(updatable = false)
	@Description("Params storage")
	private byte[] params_store;
}
