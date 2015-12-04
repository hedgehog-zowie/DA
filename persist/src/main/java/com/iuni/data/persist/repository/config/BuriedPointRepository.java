package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.BuriedGroup;
import com.iuni.data.persist.domain.config.BuriedPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface BuriedPointRepository extends JpaRepository<BuriedPoint, Long>, QueryDslPredicateExecutor<BuriedPoint> {

    List<BuriedPoint> findByStatusAndCancelFlag(int status, int cancelFlag);

}
