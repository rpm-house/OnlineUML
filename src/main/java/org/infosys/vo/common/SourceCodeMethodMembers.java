package org.infosys.vo.common;

import java.io.Serializable;
import java.util.List;

public class SourceCodeMethodMembers implements Serializable {

	private static final long serialVersionUID = 6032152099708058077L;

	private String name;
	
	private String id;

	private String returnType;

	private String typeOfReturnType;
	
	private List<String> modifiers;
	
	private List<String> actualModifiers;

	private List<String> parameters;
	
	private List<String> parameterPair;
	
	private String body;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}


	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}

	public List<String> getActualModifiers() {
		return actualModifiers;
	}

	public void setActualModifiers(List<String> actualModifiers) {
		this.actualModifiers = actualModifiers;
	}

	
	public List<String> getParameterPair() {
		return parameterPair;
	}

	public void setParameterPair(List<String> parameterPair) {
		this.parameterPair = parameterPair;
	}

	public String getTypeOfReturnType() {
		return typeOfReturnType;
	}

	public void setTypeOfReturnType(String typeOfReturnType) {
		this.typeOfReturnType = typeOfReturnType;
	}

	@Override
	public String toString() {
		return "SourceCodeMethodMembers [name=" + name + ", id=" + id + ", returnType=" + returnType
				+ ", typeOfReturnType=" + typeOfReturnType + ", modifiers=" + modifiers + ", actualModifiers="
				+ actualModifiers + ", parameters=" + parameters + ", parameterPair=" + parameterPair + ", body=" + body
				+ "]";
	}

	
	
}
