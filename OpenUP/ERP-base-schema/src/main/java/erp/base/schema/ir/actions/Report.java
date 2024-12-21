package erp.base.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.ir.Model;
import erp.base.schema.report.PaperFormat;
import erp.base.schema.res.Groups;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_act_report_xml")
@Description("Report Action")
public class Report extends Actions {
	
	public enum ReportType {
		@Description("HTML")
		qweb_html, 
		@Description("PDF")
        qweb_pdf, 
        @Description("Text")
        qweb_text
	}
	
	public class ReportTypeAttributeConverter extends EnumAttributeConverter<ReportType> {
		public ReportTypeAttributeConverter() {
			super(ReportType.class, null, null, null);
		}
	}

	@Column
	@DefaultValue("ir.actions.report")
	private String type = "ir.actions.report";
	
	@Column(nullable = false)
	@NotNull
	@Description("Model Name")
	private String model;
	
	@Transient
	@Description("Model")
	private Integer model_id;
	
	@JoinColumn(name = "model_id")
	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@Description("Model")
	private Model model_;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Convert(converter = ReportTypeAttributeConverter.class)
	@NotNull
	@DefaultValue("qweb-pdf")
	private ReportType report_type = ReportType.qweb_pdf;
	
	@Column(nullable = false)
	@NotNull
	@Description("Template Name")
	private String report_name;
	
	@Column
	@Description("Report File")
	private String report_file;
	
	@ElementCollection
	@CollectionTable(name = "res_groups_report_rel", joinColumns = {@JoinColumn(name = "uid")})
	@Column(name = "gid")
	@Description("Groups")
	private List<Integer> groups_id;

	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "res_groups_report_rel", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	private List<Groups> groups;
	
	@Column
	@Description("On Multiple Doc.")
	private Boolean multi;
	
	@Column(insertable = false, updatable = false)
	@Description("Paper Format")
	private Integer paperformat_id;
	
	@ManyToOne(targetEntity = PaperFormat.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "paperformat_id")
	private PaperFormat paperformat;
	
	@Column(columnDefinition = "jsonb")
	@Description("Printed Report Name")
	private String print_report_name;
	
	@Column
	@Description("Reload from Attachment")
	private Boolean attachment_use;
	
	@Column
	@Description("Save as Attachment Prefix")
	private String attachment;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getModel_id() {
		return model_id;
	}

	public void setModel_id(Integer model_id) {
		this.model_id = model_id;
	}

	public ReportType getReport_type() {
		return report_type;
	}

	public void setReport_type(ReportType report_type) {
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

	public List<Integer> getGroups_id() {
		return groups_id;
	}

	public void setGroups_id(List<Integer> groups_id) {
		this.groups_id = groups_id;
	}

	public Boolean getMulti() {
		return multi;
	}

	public void setMulti(Boolean multi) {
		this.multi = multi;
	}

	public Integer getPaperformat_id() {
		return paperformat_id;
	}

	public void setPaperformat_id(Integer paperformat_id) {
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

	public List<Groups> getGroups() {
		return groups;
	}

	public void setGroups(List<Groups> groups) {
		this.groups = groups;
	}

	public PaperFormat getPaperformat() {
		return paperformat;
	}

	public void setPaperformat(PaperFormat paperformat) {
		this.paperformat = paperformat;
	}
}
