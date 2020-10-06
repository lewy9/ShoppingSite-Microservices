/**
 * 
 */
package com.example.demodeal.security.property;

public class OAuth2ClientProperties {
	
	/**
	 * Third party appId
	 */
	private String clientId;
	/**
	 * Third party appSecret
	 */
	private String clientSecret;

	private int accessTokenValidateSeconds = 7200;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public int getAccessTokenValidateSeconds() {
		return accessTokenValidateSeconds;
	}

	public void setAccessTokenValidateSeconds(int accessTokenValidateSeconds) {
		this.accessTokenValidateSeconds = accessTokenValidateSeconds;
	}
	
}
