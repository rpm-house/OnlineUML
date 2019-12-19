package org.infosys.vo.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class User {
	
	private String name;
	
	private String password;
	
	private String loginPassword;
	
	private String oldPassword;
	
	private String newPassword;
	
	private String loginMail;
	
	private String recoveryMail;
	
	private String recoveryMobile;
	
	private String loginMobile;
	
	private String mobile;
	
	private String email;
	
	private String workspace;
	
	private String page;
	
	private String oneTimePassword;
	
	private String userOneTimePassword;
	
	private String statusMessage;
	
	private int statusCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getLoginMail() {
		return loginMail;
	}

	public void setLoginMail(String loginMail) {
		this.loginMail = loginMail;
	}

	public String getLoginMobile() {
		return loginMobile;
	}

	public void setLoginMobile(String loginMobile) {
		this.loginMobile = loginMobile;
	}

	public String getRecoveryMail() {
		return recoveryMail;
	}

	public void setRecoveryMail(String recoveryMail) {
		this.recoveryMail = recoveryMail;
	}

	public String getRecoveryMobile() {
		return recoveryMobile;
	}

	public void setRecoveryMobile(String recoveryMobile) {
		this.recoveryMobile = recoveryMobile;
	}

	public String getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getUserOneTimePassword() {
		return userOneTimePassword;
	}

	public void setUserOneTimePassword(String userOneTimePassword) {
		this.userOneTimePassword = userOneTimePassword;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", loginPassword=" + loginPassword + ", oldPassword="
				+ oldPassword + ", newPassword=" + newPassword + ", loginMail=" + loginMail + ", recoveryMail="
				+ recoveryMail + ", recoveryMobile=" + recoveryMobile + ", loginMobile=" + loginMobile + ", mobile="
				+ mobile + ", email=" + email + ", workspace=" + workspace + ", page=" + page + ", oneTimePassword="
				+ oneTimePassword + ", userOneTimePassword=" + userOneTimePassword + ", statusMessage=" + statusMessage
				+ ", statusCode=" + statusCode + "]";
	}

		
}
