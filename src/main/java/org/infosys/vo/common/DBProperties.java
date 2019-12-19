package org.infosys.vo.common;

import java.io.Serializable;

public class DBProperties implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -833371462535437870L;

	String DB;
	
	String URL;
	
	String userName;
	
	String password;
	
	String statusMessage;
	
	String projectPath;
	
	String projectName;
	
	String packageName;

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getDB() {
		return DB;
	}

	public void setDB(String dB) {
		DB = dB;
	}

	@Override
	public String toString() {
		return "DBProperties [DB=" + DB + ", URL=" + URL + ", userName=" + userName + ", password=" + password
				+ ", statusMessage=" + statusMessage + ", projectPath=" + projectPath + ", projectName=" + projectName
				+ ", packageName=" + packageName + "]";
	}

	

	
	
}
