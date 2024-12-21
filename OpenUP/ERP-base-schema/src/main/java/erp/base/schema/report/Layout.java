package erp.base.schema.report;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.ui.View;
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
@Table(name = "report_layout")
@Description("Report Layout")
public class Layout {
	
	@Id
	private int id;

	@Transient
	private Integer view_id;
	
	@ManyToOne(targetEntity = View.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "view_id", nullable = false)
	@NotNull
	@Description("Document Template")
	private View view;
	
	@Column
	@Description("Preview image src")
	private String image;
	
	@Column
	@Description("Preview pdf src")
	private String pdf;
	
	@Column
	@DefaultValue("50")
	private Integer sequence = 50;
	
	@Column
	private String name;
}
