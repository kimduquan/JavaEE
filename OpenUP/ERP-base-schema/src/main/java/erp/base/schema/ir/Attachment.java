package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_attachment")
@Description("Attachment")
public class Attachment {

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
	@Column
	@Description("Description")
	private String description;
	
	/**
	 * 
	 */
	@Column
	@Description("Resource Name")
	private String res_name;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Resource Model")
	private String res_model;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Resource Field")
	private String res_field;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@ManyToOne(targetEntity = Model.class)
	@Description("Resource ID")
	private String res_id;
	
	/**
	 * 
	 */
	@Column
	@ManyToOne(targetEntity = Company.class)
	@Description("Company")
	private String company_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("binary")
	@Description("Type")
	private String type = "binary";
	
	/**
	 * 
	 */
	@Column(length = 1024)
	@Description("Url")
	private String url;
	
	/**
	 * 
	 */
	@Column(name = "public")
	@Description("Is public document")
	private Boolean _public;
	
	/**
	 * 
	 */
	@Column
	@Description("Access Token")
	private String access_token;
	
	/**
	 * 
	 */
	@Column
	@Description("File Content (raw)")
	private byte[] raw;
	
	/**
	 * 
	 */
	@Column
	@Description("File Content (base64)")
	private byte[] datas;
	
	/**
	 * 
	 */
	@Column
	@Description("Database Data")
	private byte[] db_datas;
	
	/**
	 * 
	 */
	@Column
	@Description("Stored Filename")
	private String store_fname;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("File Size")
	private Integer file_size;
	
	/**
	 * 
	 */
	@Column(updatable = false, length = 40)
	@Description("Checksum/SHA1")
	private String checksum;
	
	/**
	 * 
	 */
	@Column(updatable = false)
	@Description("Mime Type")
	private String mimetype;
	
	/**
	 * 
	 */
	@Column
	@Description("Indexed Content")
	private String index_content;
}
