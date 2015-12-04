package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.BuriedGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface BuriedGroupRepository extends JpaRepository<BuriedGroup, Long>, QueryDslPredicateExecutor<BuriedGroup> {

    List<BuriedGroup> findByStatusAndCancelFlag(int status, int cancelFlag);

}
