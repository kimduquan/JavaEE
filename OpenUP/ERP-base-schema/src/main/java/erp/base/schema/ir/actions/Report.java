package erp.base.schema.ir.actions;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.Model;
import erp.base.schema.report.PaperFormat;
import erp.base.schema.res.Groups;
import erp.schema.util.EnumAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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
	private Integer model_id;
	
	@Transient
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
	
	@Transient
	private List<Integer> groups_id;

	@ManyToMany(targetEntity = Groups.class)
	@JoinTable(name = "res_groups_report_rel", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "gid")})
	@Description("Groups")
	private List<Groups> groups;
	
	@Column
	@Description("On Multiple Doc.")
	private Boolean multi;
	
	@Transient
	private Integer paperformat_id;
	
	@ManyToOne(targetEntity = PaperFormat.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "paperformat_id")
	@Description("Paper Format")
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
}
