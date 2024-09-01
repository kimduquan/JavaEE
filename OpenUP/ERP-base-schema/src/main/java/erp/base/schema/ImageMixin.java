package erp.base.schema;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "image_mixin")
@Description("Image Mixin")
@NodeEntity("Image Mixin")
public class ImageMixin {

	/**
	 * 
	 */
	@Column
	@Description("Image")
	@Property
	private String image_1920;
	
	/**
	 * 
	 */
	@Column
	@Description("Image 1024")
	@Property
	private String image_1024;
	
	/**
	 * 
	 */
	@Column
	@Description("Image 512")
	@Property
	private String image_512;
	
	/**
	 * 
	 */
	@Column
	@Description("Image 256")
	@Property
	private String image_256;
	
	/**
	 * 
	 */
	@Column
	@Description("Image 128")
	@Property
	private String image_128;

	public String getImage_1920() {
		return image_1920;
	}

	public void setImage_1920(String image_1920) {
		this.image_1920 = image_1920;
	}

	public String getImage_1024() {
		return image_1024;
	}

	public void setImage_1024(String image_1024) {
		this.image_1024 = image_1024;
	}

	public String getImage_512() {
		return image_512;
	}

	public void setImage_512(String image_512) {
		this.image_512 = image_512;
	}

	public String getImage_256() {
		return image_256;
	}

	public void setImage_256(String image_256) {
		this.image_256 = image_256;
	}

	public String getImage_128() {
		return image_128;
	}

	public void setImage_128(String image_128) {
		this.image_128 = image_128;
	}
}
