package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import erp.base.schema.res.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_attachment")
@Description("Attachment")
public class Attachment {
	
	public enum Type {
		@Description("URL")
		url,
		@Description("File")
		binary
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	@Column
	@Description("Description")
	private String description;
	
	@Transient
	@Description("Resource Name")
	private String res_name;
	
	@Column(updatable = false)
	@Description("Resource Model")
	private String res_model;
	
	@Column(updatable = false)
	@Description("Resource Field")
	private String res_field;
	
	@Transient
	private Integer res_id;

	@ManyToOne(targetEntity = Model.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "res_id", updatable = false)
	@Description("Resource ID")
	private Model resource;
	
	@Transient
	private Integer company_id;

	@ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@Description("Company")
	private Company company;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@NotNull
	@DefaultValue("binary")
	@Description("Type")
	private Type type = Type.binary;
	
	@Column(length = 1024)
	@Description("Url")
	private String url;
	
	@Column(name = "public")
	@Description("Is public document")
	private Boolean _public;
	
	@Column
	@Description("Access Token")
	private String access_token;
	
	@Column
	@Description("File Content (raw)")
	private byte[] raw;
	
	@Column
	@Description("File Content (base64)")
	private byte[] datas;
	
	@Column
	@Description("Database Data")
	private byte[] db_datas;
	
	@Column
	@Description("Stored Filename")
	private String store_fname;
	
	@Column(updatable = false)
	@Description("File Size")
	private Integer file_size;
	
	@Column(updatable = false, length = 40)
	@Description("Checksum/SHA1")
	private String checksum;
	
	@Column(updatable = false)
	@Description("Mime Type")
	private String mimetype;
	
	@Column
	@Description("Indexed Content")
	private String index_content;
}
