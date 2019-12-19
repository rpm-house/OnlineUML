package org.infosys.biz;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

public class CallFromWorkspace {

	public static void main(String[] args) {
	CallFromWorkspace callFromWorkspace = new CallFromWorkspace();
	callFromWorkspace.getProjects();
	}
	
	
	public IProject[] getProjects(){
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		return projects;
		}
}
