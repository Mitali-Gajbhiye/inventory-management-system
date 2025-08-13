package com.learn.IMS.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.learn.IMS.model.AuditLog;
import com.learn.IMS.repository.AuditLogsRepository;
import com.learn.IMS.service.AuditLogsService;

@Service
public class AuditServiceImpl implements AuditLogsService{
	
	@Autowired
	private AuditLogsRepository auditLogRepo;

	@Override
	public void logAction(String action, String userName, String details) {
		 AuditLog log = new AuditLog();
		 log.setAction(action);
		 log.setDetails(details);
		 log.setUsername(userName);
		 log.setTimestamp(LocalDateTime.now());
		 
		 auditLogRepo.save(log);
	}

	@Override
	public List<AuditLog> getAllLogs() {
		return auditLogRepo.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
	}

}
