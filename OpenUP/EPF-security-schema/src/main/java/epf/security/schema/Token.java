/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.security.schema;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author FOXCONN
 */
@XmlRootElement
public class Token implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     */
    private String name;
    /**
     * 
     */
    private String rawToken;
    /**
     * 
     */
    private String issuer;
    /**
     * 
     */
    private Set<String> audience;
    /**
     * 
     */
    private String subject;
    /**
     * 
     */
    private String tokenID;
    /**
     * 
     */
    private long expirationTime;
    /**
     * 
     */
    private long issuedAtTime;
    /**
     * 
     */
    private Set<String> groups;
    /**
     * 
     */
    private Map<? extends String, ? extends Object> claims;
    
    @Override
    public String toString() {
    	return String.valueOf(tokenID);
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getRawToken() {
        return rawToken;
    }

    public void setRawToken(final String rawToken) {
        this.rawToken = rawToken;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(final String issuer) {
        this.issuer = issuer;
    }

    public Set<String> getAudience() {
        return audience;
    }

    public void setAudience(final Set<String> audience) {
        this.audience = audience;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getTokenID() {
        return tokenID;
    }

    public void setTokenID(final String tokenID) {
        this.tokenID = tokenID;
    }

    public long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(final long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public long getIssuedAtTime() {
        return issuedAtTime;
    }

    public void setIssuedAtTime(final long issuedAtTime) {
        this.issuedAtTime = issuedAtTime;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(final Set<String> groups) {
        this.groups = groups;
    }
    
    public Map<? extends String, ? extends Object> getClaims() {
		return claims;
	}

	public void setClaims(final Map<? extends String, ? extends Object> claims) {
		this.claims = claims;
	}
}
