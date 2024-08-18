package erp.base.schema.res;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_lang")
@Description("Languages")
public class Lang {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Locale Code")
	private String code;
	
	/**
	 * 
	 */
	@Column
	@Description("ISO code")
	private String iso_code;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("URL Code")
	private String url_code;
	
	/**
	 * 
	 */
	@Column
	private Boolean active;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("ltr")
	private String direction = "ltr";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("%m/%d/%Y")
	@Description("Date Format")
	private String date_format = "%m/%d/%Y";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("%H:%M:%S")
	@Description("Time Format")
	private String time_format = "%H:%M:%S";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("7")
	@Description("First Day of Week")
	private String week_start = "7";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("[]")
	@Description("Separator Format")
	private String grouping = "[]";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue(".")
	@Description("Decimal Separator")
	private String decimal_point = ".";
	
	/**
	 * 
	 */
	@Column
	@DefaultValue(",")
	@Description("Thousands Separator")
	private String thousands_sep = ",";
}
