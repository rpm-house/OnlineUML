package org.infosys.vo.common;

import java.io.Serializable;
import java.util.List;

public class SourceCodeFieldMembers implements Serializable {

	private static final long serialVersionUID = -7783314158623900391L;

	private String name;
	
	private String actualName;
	
	private String simpleName;
	
	private String initializer;

	private String dataType;
	
	private String typeOfDataType;
	
	private String id;

	private List<String> modifiers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public List<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActualName() {
		return actualName;
	}

	public void setActualName(String actualName) {
		this.actualName = actualName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getInitializer() {
		return initializer;
	}

	public void setInitializer(String initializer) {
		this.initializer = initializer;
	}

	public String getTypeOfDataType() {
		return typeOfDataType;
	}

	public void setTypeOfDataType(String typeOfDataType) {
		this.typeOfDataType = typeOfDataType;
	}

		
	
	

}
