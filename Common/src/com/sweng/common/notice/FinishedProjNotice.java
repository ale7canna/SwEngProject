package com.sweng.common.notice;

import java.util.Date;

import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.utils.DefaultMessages;

public class FinishedProjNotice extends Notice {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ProjectInfo projectInfo; 
	public FinishedProjNotice(Integer _id, ProjectInfo _projectInfo) {
		super(_id, DefaultMessages.FinishedProjectTitle.toString(), DefaultMessages.FinishedProject.toString());
		projectInfo = _projectInfo;
	}
	
	ProjectInfo getProjectInfo()
	{
		return projectInfo;
	}

}
