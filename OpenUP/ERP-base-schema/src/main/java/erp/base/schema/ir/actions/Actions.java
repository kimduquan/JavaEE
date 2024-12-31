package erp.base.schema.ir.actions;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "ir_actions")
@Description("Actions")
public class Actions {
	
	public enum BindingType {
		@Description("Action")
		action,
		@Description("Report")
        report
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Action Name")
	private String name;
	
	@Column(nullable = false)
	@NotNull
	@Description("Action Type")
	private String type;
	
	@Transient
	@Description("External ID")
	private String xml_id;
	
	@Column
	private String path;
	
	@Column
	@Description("Action Description")
	private String help;
	
	@Transient
	private Integer binding_model_id;
	
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "binding_model_id")
	private Model binding_model;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("action")
	private BindingType binding_type = BindingType.action;
	
	@Column
	@DefaultValue("list,form")
	private String binding_view_types = "list,form";
}
