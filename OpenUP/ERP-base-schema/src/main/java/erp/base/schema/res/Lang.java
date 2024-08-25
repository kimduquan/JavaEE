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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIso_code() {
		return iso_code;
	}

	public void setIso_code(String iso_code) {
		this.iso_code = iso_code;
	}

	public String getUrl_code() {
		return url_code;
	}

	public void setUrl_code(String url_code) {
		this.url_code = url_code;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getDate_format() {
		return date_format;
	}

	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}

	public String getTime_format() {
		return time_format;
	}

	public void setTime_format(String time_format) {
		this.time_format = time_format;
	}

	public String getWeek_start() {
		return week_start;
	}

	public void setWeek_start(String week_start) {
		this.week_start = week_start;
	}

	public String getGrouping() {
		return grouping;
	}

	public void setGrouping(String grouping) {
		this.grouping = grouping;
	}

	public String getDecimal_point() {
		return decimal_point;
	}

	public void setDecimal_point(String decimal_point) {
		this.decimal_point = decimal_point;
	}

	public String getThousands_sep() {
		return thousands_sep;
	}

	public void setThousands_sep(String thousands_sep) {
		this.thousands_sep = thousands_sep;
	}
}
