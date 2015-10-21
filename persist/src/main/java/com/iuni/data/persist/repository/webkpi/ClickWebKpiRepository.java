package com.iuni.data.persist.repository.webkpi;

import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Date;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ClickWebKpiRepository extends JpaRepository<ClickWebKpi, Long>, QueryDslPredicateExecutor<ClickWebKpi> {

    List<ClickWebKpi> findByTimeAndTtype(Date time, String tType);

}
