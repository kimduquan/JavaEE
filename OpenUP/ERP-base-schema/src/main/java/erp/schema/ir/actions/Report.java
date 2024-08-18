package erp.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;

import erp.schema.ReportPaperFormat;
import erp.schema.ir.Model;
import erp.schema.res.Groups;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_act_report_xml")
@Description("Report Action")
public class Report extends Actions {

	/**
	 * 
	 */
	@Column
	private String type = "ir.actions.report";
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	private String binding_type = "report";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Model Name")
	private String model;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Model.class)
	@Description("Model")
	private String model_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("qweb-pdf")
	private String report_type;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Template Name")
	private String report_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Report File")
	private String report_file;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@Description("Groups")
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@Column
	@Description("On Multiple Doc.")
	private Boolean multi;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = ReportPaperFormat.class)
	@Description("Paper Format")
	private String paperformat_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Printed Report Name")
	private String print_report_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Reload from Attachment")
	private Boolean attachment_use;
	
	/**
	 * 
	 */
	@Column
	@Description("Save as Attachment Prefix")
	private String attachment;
}
