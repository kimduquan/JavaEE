package erp.base.schema.report;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.Transient;
import erp.base.schema.ir.act.Report;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "report_paperformat")
@Description("Paper Format Config")
@NodeEntity("Paper Format Config")
public class PaperFormat {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column(name = "default")
	@Description("Default paper format?")
	@Property
	private Boolean _default;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("A4")
	@Description("Paper size")
	@Property
	private String format = "A4";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("40")
	@Description("Top Margin (mm)")
	@Property
	private Float margin_top = Float.valueOf(40);
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("20")
	@Description("Bottom Margin (mm)")
	@Property
	private Float margin_bottom = Float.valueOf(20);
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("7")
	@Description("Left Margin (mm)")
	@Property
	private Float margin_left = Float.valueOf(7);
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("7")
	@Description("Right Margin (mm)")
	@Property
	private Float margin_right = Float.valueOf(7);
	
	/**
	 * 
	 */
	@Column
	@Description("Page height (mm)")
	@Property
	private Integer page_height;
	
	/**
	 * 
	 */
	@Column
	@Description("Page width (mm)")
	@Property
	private Integer page_width;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("Landscape")
	@Description("Orientation")
	@Property
	private String orientation = "Landscape";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Display a header line")
	@Property
	private Boolean header_line = false;
	
	/**
	 * 
	 */
	@Column
	@Description("Header spacing")
	@DefaultValue("35")
	@Property
	private Integer header_spacing = 35;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("disable_shrinking")
	@Property
	private Boolean disable_shrinking;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("90")
	@Description("Output DPI")
	@Property
	private Integer dpi = 90;
	
	/**
	 * 
	 */
	@ElementCollection(targetClass = Report.class)
	@CollectionTable(name = "ir_actions_report", joinColumns = {
			@JoinColumn(name = "paperformat_id")
	})
	@Description("Associated reports")
	@Transient
	private List<String> report_ids;
	
	/**
	 * 
	 */
	@OneToMany(targetEntity = Report.class)
	@JoinTable(name = "ir_actions_report", joinColumns = {
			@JoinColumn(name = "paperformat_id")
	})
	@Relationship(type = "ASSOCIATED_REPORTS")
	private List<Report> reports;
	
	/**
	 * 
	 */
	@Column
	@Description("Print page width (mm)")
	@Property
	private Float print_page_width;
	
	/**
	 * 
	 */
	@Column
	@Description("Print page height (mm)")
	@Property
	private Float print_page_height;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean get_default() {
		return _default;
	}

	public void set_default(Boolean _default) {
		this._default = _default;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Float getMargin_top() {
		return margin_top;
	}

	public void setMargin_top(Float margin_top) {
		this.margin_top = margin_top;
	}

	public Float getMargin_bottom() {
		return margin_bottom;
	}

	public void setMargin_bottom(Float margin_bottom) {
		this.margin_bottom = margin_bottom;
	}

	public Float getMargin_left() {
		return margin_left;
	}

	public void setMargin_left(Float margin_left) {
		this.margin_left = margin_left;
	}

	public Float getMargin_right() {
		return margin_right;
	}

	public void setMargin_right(Float margin_right) {
		this.margin_right = margin_right;
	}

	public Integer getPage_height() {
		return page_height;
	}

	public void setPage_height(Integer page_height) {
		this.page_height = page_height;
	}

	public Integer getPage_width() {
		return page_width;
	}

	public void setPage_width(Integer page_width) {
		this.page_width = page_width;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Boolean getHeader_line() {
		return header_line;
	}

	public void setHeader_line(Boolean header_line) {
		this.header_line = header_line;
	}

	public Integer getHeader_spacing() {
		return header_spacing;
	}

	public void setHeader_spacing(Integer header_spacing) {
		this.header_spacing = header_spacing;
	}

	public Boolean getDisable_shrinking() {
		return disable_shrinking;
	}

	public void setDisable_shrinking(Boolean disable_shrinking) {
		this.disable_shrinking = disable_shrinking;
	}

	public Integer getDpi() {
		return dpi;
	}

	public void setDpi(Integer dpi) {
		this.dpi = dpi;
	}

	public List<String> getReport_ids() {
		return report_ids;
	}

	public void setReport_ids(List<String> report_ids) {
		this.report_ids = report_ids;
	}

	public Float getPrint_page_width() {
		return print_page_width;
	}

	public void setPrint_page_width(Float print_page_width) {
		this.print_page_width = print_page_width;
	}

	public Float getPrint_page_height() {
		return print_page_height;
	}

	public void setPrint_page_height(Float print_page_height) {
		this.print_page_height = print_page_height;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}
}
