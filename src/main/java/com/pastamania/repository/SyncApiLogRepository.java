package com.pastamania.repository;

import com.pastamania.entity.ApiLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncApiLogRepository extends JpaRepository<ApiLog, Long> {
}
