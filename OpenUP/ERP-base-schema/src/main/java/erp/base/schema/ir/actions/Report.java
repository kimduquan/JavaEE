package erp.base.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.ir.model.Model;
import erp.base.schema.report.PaperFormat;
import erp.base.schema.res.groups.Groups;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
	public enum ReportType {
		/**
		 * 
		 */
		qweb_html,
        /**
         * 
         */
        qweb_pdf,
        /**
         * 
         */
        qweb_text
	}
	
	/**
	 * 
	 */
	public class ReportTypeAttributeConverter extends EnumAttributeConverter<ReportType> {
		/**
		 * 
		 */
		public ReportTypeAttributeConverter() {
			super(ReportType.class, null, null, null);
		}
	}
	
	/**
	 * 
	 */
	public Report() {
		setBinding_type(BindingType.report);
	}

	/**
	 * 
	 */
	@Column
	@Property
	private String type = "ir.actions.report";
	
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
	@Description("Model")
	@Transient
	private String model_id;

	/**
	 * 
	 */
	@ManyToOne(targetEntity = Model.class)
	@JoinColumn(name = "model_id")
	@Relationship(type = "MODEL")
	private Model _model;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Convert(converter = ReportTypeAttributeConverter.class)
	@NotNull
	@Description("qweb-pdf")
	@Property
	private ReportType report_type = ReportType.qweb_pdf;
	
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
	@ElementCollection(targetClass = Groups.class)
	@CollectionTable(name = "res_groups_report_rel", joinColumns = {
			@JoinColumn(name = "uid", referencedColumnName = "gid")
	})
	@Description("Groups")
	@Transient
	private List<String> groups_id;

	/**
	 * 
	 */
	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "res_groups_report_rel", joinColumns = {
			@JoinColumn(name = "uid", referencedColumnName = "gid")
	})
	@Relationship(type = "GROUPS")
	private List<Groups> groups;
	
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
	@Description("Paper Format")
	@Transient
	private String paperformat_id;
	
	/**
	 * 
	 */
	@ManyToOne(targetEntity = PaperFormat.class)
	@JoinColumn(name = "paperformat_id")
	@Relationship(type = "PAPER_FORMAT")
	private PaperFormat paperformat;
	
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

	public Model get_model() {
		return _model;
	}

	public void set_model(Model _model) {
		this._model = _model;
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
