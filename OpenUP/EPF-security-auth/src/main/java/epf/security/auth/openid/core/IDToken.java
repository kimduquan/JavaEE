package epf.security.auth.openid.core;

/**
 * @author PC
 *
 */
public class IDToken {

	/**
	 * 
	 */
	private String iss;
	/**
	 * 
	 */
	private String sub;
	/**
	 * 
	 */
	private String[] aud;
	/**
	 * 
	 */
	private Long exp;
	/**
	 * 
	 */
	private Long iat;
	/**
	 * 
	 */
	private Long auth_time;
	/**
	 * 
	 */
	private String nonce;
	/**
	 * 
	 */
	private String acr;
	/**
	 * 
	 */
	private String[] amr;
	/**
	 * 
	 */
	private String azp;
	
	public String getIss() {
		return iss;
	}
	public void setIss(final String iss) {
		this.iss = iss;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(final String sub) {
		this.sub = sub;
	}
	public String[] getAud() {
		return aud;
	}
	public void setAud(final String[] aud) {
		this.aud = aud;
	}
	public Long getExp() {
		return exp;
	}
	public void setExp(final Long exp) {
		this.exp = exp;
	}
	public Long getIat() {
		return iat;
	}
	public void setIat(final Long iat) {
		this.iat = iat;
	}
	public Long getAuth_time() {
		return auth_time;
	}
	public void setAuth_time(final Long auth_time) {
		this.auth_time = auth_time;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(final String nonce) {
		this.nonce = nonce;
	}
	public String getAcr() {
		return acr;
	}
	public void setAcr(final String acr) {
		this.acr = acr;
	}
	public String[] getAmr() {
		return amr;
	}
	public void setAmr(final String[] amr) {
		this.amr = amr;
	}
	public String getAzp() {
		return azp;
	}
	public void setAzp(final String azp) {
		this.azp = azp;
	}
}
