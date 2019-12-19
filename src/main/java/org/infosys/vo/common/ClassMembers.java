package org.infosys.vo.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClassMembers implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8775529309985848036L;

	String className;

	String type;
	
	String id;
	
	private Map<String, String> nameIdMap;
	
private List<String> importList;
	
	private String classModifiers;
	
	private String packageName;
	
	private String superClass;

	private String superInterfaces;



	List<String> attributes;
	
	List<Map<String,String>> attributeList;

	List<String> methods;
	
	private List<Map<String,String>> methodList;
	
	List<RelationMembers> relationList = new ArrayList<RelationMembers>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public List<String> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<String> attributes) {
		this.attributes = attributes;
	}

	public List<String> getMethods() {
		return methods;
	}

	public void setMethods(List<String> methods) {
		this.methods = methods;
	}



	

	public List<Map<String, String>> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<Map<String, String>> attributeList) {
		this.attributeList = attributeList;
	}

	public List<RelationMembers> getRelationList() {
		return relationList;
	}

	public void setRelationList(List<RelationMembers> relationList) {
		this.relationList = relationList;
	}

	

	public List<Map<String, String>> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<Map<String, String>> methodList) {
		this.methodList = methodList;
	}

	public List<String> getImportList() {
		return importList;
	}

	public void setImportList(List<String> importList) {
		this.importList = importList;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public String getClassModifiers() {
		return classModifiers;
	}

	public void setClassModifiers(String classModifiers) {
		this.classModifiers = classModifiers;
	}

	public String getSuperInterfaces() {
		return superInterfaces;
	}

	public void setSuperInterfaces(String superInterfaces) {
		this.superInterfaces = superInterfaces;
	}

	public Map<String, String> getNameIdMap() {
		return nameIdMap;
	}

	public void setNameIdMap(Map<String, String> nameIdMap) {
		this.nameIdMap = nameIdMap;
	}

	@Override
	public String toString() {
		return "ClassMembers [className=" + className + ", type=" + type + ", id=" + id + ", nameIdMap=" + nameIdMap
				+ ", importList=" + importList + ", classModifiers=" + classModifiers + ", packageName=" + packageName
				+ ", superClass=" + superClass + ", superInterfaces=" + superInterfaces + ", attributes=" + attributes
				+ ", attributeList=" + attributeList + ", methods=" + methods + ", methodList=" + methodList
				+ ", relationList=" + relationList + "]";
	}

	

	
	

	
	
}
