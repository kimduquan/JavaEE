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
import jakarta.persistence.JoinTable;
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
	@JoinTable(name = "ir_act_report_xml", joinColumns = {
			@JoinColumn(name = "paperformat_id", referencedColumnName = "id")
	})
	@Description("Associated reports")
	private List<Report> reports;
	
	@Column
	@Description("Print page width (mm)")
	private Float print_page_width;
	
	@Column
	@Description("Print page height (mm)")
	private Float print_page_height;
	
	@Column
	@DefaultValue("false")
	@Description("Use css margins")
	private Boolean css_margins = false;
}
