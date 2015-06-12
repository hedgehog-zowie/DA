package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.RTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface PageTagRepository extends JpaRepository<RTag, Long>, QueryDslPredicateExecutor<RTag> {

    List<RTag> findByStatusAndCancelFlag(int status, int cancelFlag);

}
