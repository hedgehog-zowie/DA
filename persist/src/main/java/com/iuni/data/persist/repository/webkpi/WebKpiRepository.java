package com.iuni.data.persist.repository.webkpi;

import com.iuni.data.persist.domain.webkpi.WebKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Date;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface WebKpiRepository extends JpaRepository<WebKpi, Long>, QueryDslPredicateExecutor<WebKpi> {

    void deleteByTimeAndTtype(Date time, String tType);

    List<WebKpi> findByTimeAndTtype(Date time, String tType);

}
