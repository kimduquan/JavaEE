package erp.base.schema.report;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.ui.View;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "report_layout")
@Description("Report Layout")
public class Layout {

	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = View.class)
	@NotNull
	@Description("Document Template")
	private String view_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Preview image src")
	private String image;
	
	/**
	 * 
	 */
	@Column
	@Description("Preview pdf src")
	private String pdf;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("50")
	private Integer sequence = 50;
	
	@Column
	private String name;
}
