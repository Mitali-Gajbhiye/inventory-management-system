package com.learn.IMS.service;

import java.util.List;

import com.learn.IMS.model.AuditLog;

public interface AuditLogsService {

	public void logAction(String action, String userName, String details);
	public List<AuditLog> getAllLogs();
	
}
