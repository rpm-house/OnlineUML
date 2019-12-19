package org.infosys.vo.json;

import com.google.gson.annotations.SerializedName;

public class Attrs {
	
	@SerializedName(".uml-class-name-rect")
	UmlClassNameRect umlClassNameRect;
	
	@SerializedName(".uml-class-attrs-rect")
	UmlClassAttrsRect umlClassAttrsRect;
	
	@SerializedName(".uml-class-methods-rect")
	UmlClassMethodsRect umlClassMethodsRect;
	
	@SerializedName(".uml-class-name-text")
	UmlClassNameText umlClassNameText;
	
	@SerializedName(".uml-class-attrs-text")
	UmlClassAttrsText umlClassAttrsText;
	
	@SerializedName(".uml-class-methods-text")
	UmlClassMethodsText umlClassMethodsText;

	public UmlClassNameRect getUmlClassNameRect() {
		return umlClassNameRect;
	}

	public void setUmlClassNameRect(UmlClassNameRect umlClassNameRect) {
		this.umlClassNameRect = umlClassNameRect;
	}

	public UmlClassAttrsRect getUmlClassAttrsRect() {
		return umlClassAttrsRect;
	}

	public void setUmlClassAttrsRect(UmlClassAttrsRect umlClassAttrsRect) {
		this.umlClassAttrsRect = umlClassAttrsRect;
	}

	public UmlClassMethodsRect getUmlClassMethodsRect() {
		return umlClassMethodsRect;
	}

	public void setUmlClassMethodsRect(UmlClassMethodsRect umlClassMethodsRect) {
		this.umlClassMethodsRect = umlClassMethodsRect;
	}

	public UmlClassNameText getUmlClassNameText() {
		return umlClassNameText;
	}

	public void setUmlClassNameText(UmlClassNameText umlClassNameText) {
		this.umlClassNameText = umlClassNameText;
	}

	public UmlClassAttrsText getUmlClassAttrsText() {
		return umlClassAttrsText;
	}

	public void setUmlClassAttrsText(UmlClassAttrsText umlClassAttrsText) {
		this.umlClassAttrsText = umlClassAttrsText;
	}

	public UmlClassMethodsText getUmlClassMethodsText() {
		return umlClassMethodsText;
	}

	public void setUmlClassMethodsText(UmlClassMethodsText umlClassMethodsText) {
		this.umlClassMethodsText = umlClassMethodsText;
	}
	
	

}
