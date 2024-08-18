package erp.schema;

import java.util.List;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.schema.ir.actions.Report;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "report_paperformat")
@Description("Paper Format Config")
public class ReportPaperFormat {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(name = "default")
	@Description("Default paper format?")
	private Boolean _default;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("A4")
	@Description("Paper size")
	private String format = "A4";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("40")
	@Description("Top Margin (mm)")
	private Float margin_top = Float.valueOf(40);
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("20")
	@Description("Bottom Margin (mm)")
	private Float margin_bottom = Float.valueOf(20);
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("7")
	@Description("Left Margin (mm)")
	private Float margin_left = Float.valueOf(7);
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("7")
	@Description("Right Margin (mm)")
	private Float margin_right = Float.valueOf(7);
	
	/**
	 * 
	 */
	@Column
	@Description("Page height (mm)")
	private Integer page_height;
	
	/**
	 * 
	 */
	@Column
	@Description("Page width (mm)")
	private Integer page_width;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("Landscape")
	@Description("Orientation")
	private String orientation = "Landscape";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("false")
	@Description("Display a header line")
	private Boolean header_line = false;
	
	/**
	 * 
	 */
	@Column
	@Description("Header spacing")
	@DefaultValue("35")
	private Integer header_spacing = 35;
	
	/**
	 * 
	 */
	@Column
	@DefaultValue("disable_shrinking")
	private Boolean disable_shrinking;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("90")
	@Description("Output DPI")
	private Integer dpi = 90;
	
	/**
	 * 
	 */
	@Column
	@OneToMany(targetEntity = Report.class)
	@ElementCollection(targetClass = Report.class)
	@CollectionTable(name = "ir_actions_report")
	@Description("Associated reports")
	private List<String> report_ids;
	
	/**
	 * 
	 */
	@Column
	@Description("Print page width (mm)")
	private Float print_page_width;
	
	/**
	 * 
	 */
	@Column
	@Description("Print page height (mm)")
	private Float print_page_height;
}
