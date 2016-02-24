package com.sweng.common.notice;

import com.sweng.common.beans.Project;
import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.utils.DefaultMessages;

public class RemovedFromProject extends Notice {
	
	ProjectInfo projectInfo;
	
	public RemovedFromProject(ProjectInfo projectInfo) {
		super(DefaultMessages.RemovedFromProjectTitle.toString(), DefaultMessages.RemovedFromProject.toString());
		
		this.projectInfo = projectInfo;
	}
	
	ProjectInfo getProjectInfo() {
		
		return projectInfo;
	}
	
	@Override
	public String getDetails() {
		
		String s = "You were removed from " + projectInfo.getName() + " project.";
		return s;
	}
	
}
