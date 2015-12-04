package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.BuriedRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface BuriedRelationRepository extends JpaRepository<BuriedRelation, Long>, QueryDslPredicateExecutor<BuriedRelation> {

    List<BuriedRelation> findByStatusAndCancelFlag(int status, int cancelFlag);

}
