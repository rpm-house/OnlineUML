package org.infosys.vo.json;

import java.io.Serializable;

public class Target implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4051655330056326662L;
	/**
	 * 
	 */
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "Target [id=" + id + "]";
	}


	
	
}
