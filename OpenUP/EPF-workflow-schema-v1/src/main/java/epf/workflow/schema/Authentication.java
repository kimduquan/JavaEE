package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Defines the mechanism used to authenticate users and workflows attempting to access a service or a resource.")
public class Authentication {

	@JsonPropertyDescription("The name of the top-level authentication definition to use. Cannot be used by authentication definitions defined at top level.")
	private String use;
	
	@JsonPropertyDescription("The basic authentication scheme to use, if any. Required if no other property has been set, otherwise ignored.")
	private BasicAuthentication basic;
	
	@JsonPropertyDescription("The bearer authentication scheme to use, if any. Required if no other property has been set, otherwise ignored.")
	private BearerAuthentication bearer;
	
	@JsonPropertyDescription("The certificate authentication scheme to use, if any. Required if no other property has been set, otherwise ignored.")
	private CertificateAuthentication certificate;
	
	@JsonPropertyDescription("The digest authentication scheme to use, if any. Required if no other property has been set, otherwise ignored.")
	private DigestAuthentication digest;
	
	@JsonPropertyDescription("The oauth2 authentication scheme to use, if any. Required if no other property has been set, otherwise ignored.")
	private OAUTH2Authentication oauth2;
	
	@JsonPropertyDescription("The oidc authentication scheme to use, if any. Required if no other property has been set, otherwise ignored.")
	private OpenIdConnectAuthentication oidc;

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public BasicAuthentication getBasic() {
		return basic;
	}

	public void setBasic(BasicAuthentication basic) {
		this.basic = basic;
	}

	public BearerAuthentication getBearer() {
		return bearer;
	}

	public void setBearer(BearerAuthentication bearer) {
		this.bearer = bearer;
	}

	public CertificateAuthentication getCertificate() {
		return certificate;
	}

	public void setCertificate(CertificateAuthentication certificate) {
		this.certificate = certificate;
	}

	public DigestAuthentication getDigest() {
		return digest;
	}

	public void setDigest(DigestAuthentication digest) {
		this.digest = digest;
	}

	public OAUTH2Authentication getOauth2() {
		return oauth2;
	}

	public void setOauth2(OAUTH2Authentication oauth2) {
		this.oauth2 = oauth2;
	}

	public OpenIdConnectAuthentication getOidc() {
		return oidc;
	}

	public void setOidc(OpenIdConnectAuthentication oidc) {
		this.oidc = oidc;
	}
}
