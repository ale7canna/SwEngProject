package com.sweng.common.notice;

import java.util.Date;

import com.sweng.common.beans.ProjectInfo;
import com.sweng.common.utils.DefaultMessages;

public class StartedProjNotice extends Notice {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ProjectInfo projectInfo;
	
	public StartedProjNotice(Integer _id, ProjectInfo _projectInfo) {
		
		super(_id, DefaultMessages.StartedProjTitle.toString(), DefaultMessages.StartedProj.toString());
		projectInfo = _projectInfo;
	}
	
	public ProjectInfo getProjectInfo()
	{
		return projectInfo;
	}
	
}
