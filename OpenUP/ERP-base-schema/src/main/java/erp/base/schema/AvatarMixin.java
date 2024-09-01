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
@Table(name = "avatar_mixin")
@Description("Avatar Mixin")
@NodeEntity("Avatar Mixin")
public class AvatarMixin extends ImageMixin {

	/**
	 * 
	 */
	@Column
	@Description("Avatar")
	@Property
	private String avatar_1920;
	
	/**
	 * 
	 */
	@Column
	@Description("Avatar 1024")
	@Property
	private String avatar_1024;
	
	/**
	 * 
	 */
	@Column
	@Description("Avatar 512")
	@Property
	private String avatar_512;
	
	/**
	 * 
	 */
	@Column
	@Description("Avatar 256")
	@Property
	private String avatar_256;
	
	/**
	 * 
	 */
	@Column
	@Description("Avatar 128")
	@Property
	private String avatar_128;

	public String getAvatar_1920() {
		return avatar_1920;
	}

	public void setAvatar_1920(String avatar_1920) {
		this.avatar_1920 = avatar_1920;
	}

	public String getAvatar_1024() {
		return avatar_1024;
	}

	public void setAvatar_1024(String avatar_1024) {
		this.avatar_1024 = avatar_1024;
	}

	public String getAvatar_512() {
		return avatar_512;
	}

	public void setAvatar_512(String avatar_512) {
		this.avatar_512 = avatar_512;
	}

	public String getAvatar_256() {
		return avatar_256;
	}

	public void setAvatar_256(String avatar_256) {
		this.avatar_256 = avatar_256;
	}

	public String getAvatar_128() {
		return avatar_128;
	}

	public void setAvatar_128(String avatar_128) {
		this.avatar_128 = avatar_128;
	}
}
