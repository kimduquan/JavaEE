package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_actions_todo")
@Description("Configuration Wizards")
public class Todo {
	
	public enum Status {
		@Description("To Do")
		open,
		@Description("Done")
		done
	}
	
	public class StatusAttributeConverter extends EnumAttributeConverter<Status> {
		public StatusAttributeConverter() {
			super(Status.class, null, null, null);
		}
	}
	
	@Id
	private int id;

	@Transient
	private Integer action_id;
	
	@ManyToOne(targetEntity = Actions.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id", nullable = false)
	@NotNull
	@Description("Action")
	private Actions action;
	
	@Column
	@DefaultValue("10")
	private Integer sequence = 10;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Convert(converter = StatusAttributeConverter.class)
	@NotNull
	@Description("Status")
	@DefaultValue("open")
	private Status state = Status.open;
	
	@Column
	private String name;
}
