package com.sweng.common.notice;

import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.utils.DefaultMessages;

public class FinishedProjNotice extends Notice {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProjectInfo projectInfo;
	
	public FinishedProjNotice(ProjectInfo _projectInfo) {
		super(DefaultMessages.FinishedProjectTitle.toString(), DefaultMessages.FinishedProject.toString());
		projectInfo = _projectInfo;
	}
	
	ProjectInfo getProjectInfo() {
		
		return projectInfo;
	}
	
	@Override
	public String getDetails() {
		
		String s = "The project " + projectInfo.getName() + " is readey to be completed.";
		return s;
	}
	
}
