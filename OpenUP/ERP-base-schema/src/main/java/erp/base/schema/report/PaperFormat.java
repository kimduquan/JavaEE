package erp.base.schema.report;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.actions.Report;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "report_paperformat")
@Description("Paper Format Config")
public class PaperFormat {
	
	public enum PaperSize {
		@Description("A0  5   841 x 1189 mm")
		A0,
		@Description("A1  6   594 x 841 mm")
		A1,
		@Description("A2  7   420 x 594 mm")
		A2,
		@Description("A3  8   297 x 420 mm")
		A3,
		@Description("A4  0   210 x 297 mm, 8.26 x 11.69 inches")
		A4,
		@Description("A5  9   148 x 210 mm")
		A5,
		@Description("A6  10  105 x 148 mm")
		A6,
		@Description("A7  11  74 x 105 mm")
		A7,
		@Description("A8  12  52 x 74 mm")
		A8,
		@Description("A9  13  37 x 52 mm")
		A9,
		@Description("B0  14  1000 x 1414 mm")
		B0,
		@Description("B1  15  707 x 1000 mm")
		B1,
		@Description("B2  17  500 x 707 mm")
		B2,
		@Description("B3  18  353 x 500 mm")
		B3,
		@Description("B4  19  250 x 353 mm")
		B4,
		@Description("B5  1   176 x 250 mm, 6.93 x 9.84 inches")
		B5,
		@Description("B6  20  125 x 176 mm")
		B6,
		@Description("B7  21  88 x 125 mm")
		B7,
		@Description("B8  22  62 x 88 mm")
		B8,
		@Description("B9  23  33 x 62 mm")
		B9,
		@Description("B10    16  31 x 44 mm")
		B10,
		@Description("C5E 24  163 x 229 mm")
		C5E,
		@Description("Comm10E 25  105 x 241 mm, U.S. Common 10 Envelope")
		Comm10E,
		@Description("DLE 26 110 x 220 mm")
		DLE,
		@Description("Executive 4   7.5 x 10 inches, 190.5 x 254 mm")
		Executive,
		@Description("Folio 27  210 x 330 mm")
		Folio,
		@Description("Ledger  28  431.8 x 279.4 mm")
		Ledger,
		@Description("Legal    3   8.5 x 14 inches, 215.9 x 355.6 mm")
		Legal,
		@Description("Letter 2 8.5 x 11 inches, 215.9 x 279.4 mm")
		Letter,
		@Description("Tabloid 29 279.4 x 431.8 mm")
		Tabloid,
		@Description("Custom")
		custom
	}
	
	public enum Orientation {
		@Description("Landscape")
		Landscape,
		@Description("Portrait")
        Portrait
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	@Column(name = "default")
	@Description("Default paper format?")
	private Boolean default_;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("A4")
	@Description("Paper size")
	private PaperSize format = PaperSize.A4;
	
	@Column
	@DefaultValue("40")
	@Description("Top Margin (mm)")
	private Float margin_top = Float.valueOf(40);
	
	@Column
	@DefaultValue("20")
	@Description("Bottom Margin (mm)")
	private Float margin_bottom = Float.valueOf(20);
	
	@Column
	@DefaultValue("7")
	@Description("Left Margin (mm)")
	private Float margin_left = Float.valueOf(7);
	
	@Column
	@DefaultValue("7")
	@Description("Right Margin (mm)")
	private Float margin_right = Float.valueOf(7);
	
	@Column
	@Description("Page height (mm)")
	private Integer page_height;
	
	@Column
	@Description("Page width (mm)")
	private Integer page_width;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("Landscape")
	@Description("Orientation")
	private Orientation orientation = Orientation.Landscape;
	
	@Column
	@DefaultValue("false")
	@Description("Display a header line")
	private Boolean header_line = false;
	
	@Column
	@Description("Header spacing")
	@DefaultValue("35")
	private Integer header_spacing = 35;
	
	@Column
	@DefaultValue("disable_shrinking")
	private Boolean disable_shrinking;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("90")
	@Description("Output DPI")
	private Integer dpi = 90;
	
	@Transient
	private List<Integer> report_ids;
	
	@OneToMany(targetEntity = Report.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "paperformat_id")
	@Description("Associated reports")
	private List<Report> reports;
	
	@Transient
	@Description("Print page width (mm)")
	private Float print_page_width;
	
	@Transient
	@Description("Print page height (mm)")
	private Float print_page_height;
	
	@Column
	@DefaultValue("false")
	@Description("Use css margins")
	private Boolean css_margins = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDefault_() {
		return default_;
	}

	public void setDefault_(Boolean default_) {
		this.default_ = default_;
	}

	public PaperSize getFormat() {
		return format;
	}

	public void setFormat(PaperSize format) {
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

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
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

	public List<Integer> getReport_ids() {
		return report_ids;
	}

	public void setReport_ids(List<Integer> report_ids) {
		this.report_ids = report_ids;
	}

	public List<Report> getReports() {
		return reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
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

	public Boolean getCss_margins() {
		return css_margins;
	}

	public void setCss_margins(Boolean css_margins) {
		this.css_margins = css_margins;
	}
}
