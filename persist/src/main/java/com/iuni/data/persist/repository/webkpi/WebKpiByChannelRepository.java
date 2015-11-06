package com.iuni.data.persist.repository.webkpi;

import com.iuni.data.persist.domain.webkpi.WebKpiByChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.Date;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface WebKpiByChannelRepository extends JpaRepository<WebKpiByChannel, Long>, QueryDslPredicateExecutor<WebKpiByChannel> {

    void deleteByTimeAndTtype(Date time, String tType);

    List<WebKpiByChannel> findByTimeAndTtype(Date time, String tType);

}
