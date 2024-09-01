package erp.base.schema.ir.act;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import erp.base.schema.ir.actions.Actions;
import erp.base.schema.ir.model.Model;
import erp.base.schema.report.PaperFormat;
import erp.base.schema.res.groups.Groups;
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
@NodeEntity("Report Action")
public class Report extends Actions {

	/**
	 * 
	 */
	@Column
	@Property
	private String type = "ir.actions.report";
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@Property
	private String binding_type = "report";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Model Name")
	@Property
	private String model;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Model.class)
	@Description("Model")
	@Property
	@Relationship(type = "MODEL")
	private String model_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@Description("qweb-pdf")
	@Property
	private String report_type;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Template Name")
	@Property
	private String report_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Report File")
	@Property
	private String report_file;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Groups.class)
	@Description("Groups")
	@Property
	@Relationship(type = "GROUPS")
	private List<String> groups_id;
	
	/**
	 * 
	 */
	@Column
	@Description("On Multiple Doc.")
	@Property
	private Boolean multi;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = PaperFormat.class)
	@Description("Paper Format")
	@Property
	@Relationship(type = "PAPER_FORMAT")
	private String paperformat_id;
	
	/**
	 * 
	 */
	@Column
	@Description("Printed Report Name")
	@Property
	private String print_report_name;
	
	/**
	 * 
	 */
	@Column
	@Description("Reload from Attachment")
	@Property
	private Boolean attachment_use;
	
	/**
	 * 
	 */
	@Column
	@Description("Save as Attachment Prefix")
	@Property
	private String attachment;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBinding_type() {
		return binding_type;
	}

	public void setBinding_type(String binding_type) {
		this.binding_type = binding_type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getReport_type() {
		return report_type;
	}

	public void setReport_type(String report_type) {
		this.report_type = report_type;
	}

	public String getReport_name() {
		return report_name;
	}

	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}

	public String getReport_file() {
		return report_file;
	}

	public void setReport_file(String report_file) {
		this.report_file = report_file;
	}

	public List<String> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<String> groups_id) {
		this.groups_id = groups_id;
	}

	public Boolean getMulti() {
		return multi;
	}

	public void setMulti(Boolean multi) {
		this.multi = multi;
	}

	public String getPaperformat_id() {
		return paperformat_id;
	}

	public void setPaperformat_id(String paperformat_id) {
		this.paperformat_id = paperformat_id;
	}

	public String getPrint_report_name() {
		return print_report_name;
	}

	public void setPrint_report_name(String print_report_name) {
		this.print_report_name = print_report_name;
	}

	public Boolean getAttachment_use() {
		return attachment_use;
	}

	public void setAttachment_use(Boolean attachment_use) {
		this.attachment_use = attachment_use;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}
