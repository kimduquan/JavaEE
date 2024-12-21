package erp.base.schema.ir.model;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_model_fields_selection")
@Description("Fields Selection")
public class Selection {
	
	@Id
	private int id;

	@Transient
	private Integer field_id;

	@ManyToOne(targetEntity = Fields.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "field_id", nullable = false)
	@NotNull
	private Fields field;
	
	@Column(nullable = false)
	@NotNull
	private String value;
	
	@Column(nullable = false)
	@NotNull
	private String name;
	
	@Column
	@DefaultValue("1000")
	private Integer sequence = 1000;
}
