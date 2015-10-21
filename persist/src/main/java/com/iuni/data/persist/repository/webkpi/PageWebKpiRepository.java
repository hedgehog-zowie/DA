package com.iuni.data.persist.repository.webkpi;

import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Date;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface PageWebKpiRepository extends JpaRepository<PageWebKpi, Long>, QueryDslPredicateExecutor<PageWebKpi> {

    List<PageWebKpi> findByTimeAndTtype(Date time, String tType);

}
