package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.ir.model.Model;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRes_name() {
		return res_name;
	}

	public void setRes_name(String res_name) {
		this.res_name = res_name;
	}

	public String getRes_model() {
		return res_model;
	}

	public void setRes_model(String res_model) {
		this.res_model = res_model;
	}

	public String getRes_field() {
		return res_field;
	}

	public void setRes_field(String res_field) {
		this.res_field = res_field;
	}

	public String getRes_id() {
		return res_id;
	}

	public void setRes_id(String res_id) {
		this.res_id = res_id;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean get_public() {
		return _public;
	}

	public void set_public(Boolean _public) {
		this._public = _public;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public byte[] getRaw() {
		return raw;
	}

	public void setRaw(byte[] raw) {
		this.raw = raw;
	}

	public byte[] getDatas() {
		return datas;
	}

	public void setDatas(byte[] datas) {
		this.datas = datas;
	}

	public byte[] getDb_datas() {
		return db_datas;
	}

	public void setDb_datas(byte[] db_datas) {
		this.db_datas = db_datas;
	}

	public String getStore_fname() {
		return store_fname;
	}

	public void setStore_fname(String store_fname) {
		this.store_fname = store_fname;
	}

	public Integer getFile_size() {
		return file_size;
	}

	public void setFile_size(Integer file_size) {
		this.file_size = file_size;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getIndex_content() {
		return index_content;
	}

	public void setIndex_content(String index_content) {
		this.index_content = index_content;
	}
}
