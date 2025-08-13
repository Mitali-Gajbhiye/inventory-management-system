package com.learn.IMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.IMS.model.AuditLog;

@Repository
public interface AuditLogsRepository extends JpaRepository<AuditLog, Long> {

}
