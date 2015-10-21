package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.UserDefinedReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface UserDefinedReportRepository extends JpaRepository<UserDefinedReport, Long>, QueryDslPredicateExecutor<UserDefinedReport> {
}
