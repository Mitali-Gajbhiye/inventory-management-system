package com.learn.IMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.learn.IMS.model.AuditLog;
import com.learn.IMS.service.AuditLogsService;

@Controller
public class AuditLogController {

	@Autowired
	private AuditLogsService auditlogServ;
	
	@GetMapping("/audit-logs")
	public String viewLogs(Model m) {
		List<AuditLog> log = auditlogServ.getAllLogs();
		m.addAttribute("logs",log);
		return "admin/logs";
	}
}
