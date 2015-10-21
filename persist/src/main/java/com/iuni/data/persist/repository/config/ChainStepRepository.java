package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.ChainStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ChainStepRepository extends JpaRepository<ChainStep, Long>, QueryDslPredicateExecutor<ChainStep> {
}
