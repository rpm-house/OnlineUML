package org.infosys.vo.common;

import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.SimpleType;


public class SourceCodeClassMembers  {

	//private static final long serialVersionUID = -6261414482117414003L;
	private String name;
	
	private String id;
	
	private Map<String, String> nameIdMap;

	private String type;

	private List<Modifier> modifiers;
	
	private List<String> modifierList;

	private String superClass;

	private List<SimpleType> superInterfaces;
	
	private List<String> superInterfaceList;

	private List<SourceCodeMethodMembers> methods;

	private List<SourceCodeFieldMembers> feilds;
	
	private List<String> importList;
	
	private String packageName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public List<Modifier> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
	}

	public String getSuperClass() {
		return superClass;
	}

	public void setSuperClass(String superClass) {
		this.superClass = superClass;
	}

	public List<SimpleType> getSuperInterfaces() {
		return superInterfaces;
	}

	public void setSuperInterfaces(List<SimpleType> superInterfaces) {
		this.superInterfaces = superInterfaces;
	}

	public List<SourceCodeMethodMembers> getMethods() {
		return methods;
	}

	public void setMethods(List<SourceCodeMethodMembers> methods) {
		this.methods = methods;
	}

	public List<SourceCodeFieldMembers> getFeilds() {
		return feilds;
	}

	public void setFeilds(List<SourceCodeFieldMembers> feilds) {
		this.feilds = feilds;
	}

	public List<String> getImportList() {
		return importList;
	}

	public void setImportList(List<String> importList) {
		this.importList = importList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getNameIdMap() {
		return nameIdMap;
	}

	public void setNameIdMap(Map<String, String> nameIdMap) {
		this.nameIdMap = nameIdMap;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	

	public List<String> getModifierList() {
		return modifierList;
	}

	public void setModifierList(List<String> modifierList) {
		this.modifierList = modifierList;
	}

	
	
	public List<String> getSuperInterfaceList() {
		return superInterfaceList;
	}

	public void setSuperInterfaceList(List<String> superInterfaceList) {
		this.superInterfaceList = superInterfaceList;
	}

	@Override
	public String toString() {
		return "SourceCodeClassMembers [name=" + name + ", id=" + id + ", nameIdMap=" + nameIdMap + ", type=" + type
				+ ", modifiers=" + modifiers + ", modifierList=" + modifierList + ", superClass=" + superClass
				+ ", superInterfaces=" + superInterfaces + ", superInterfaceList=" + superInterfaceList + ", methods="
				+ methods + ", feilds=" + feilds + ", importList=" + importList + ", packageName=" + packageName + "]";
	}

	

	
	
	
}
