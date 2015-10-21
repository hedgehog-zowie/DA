package com.iuni.data.persist.repository.weixin;

import com.iuni.data.persist.domain.weixin.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface BillDetailRepository extends JpaRepository<BillDetail, Long>, QueryDslPredicateExecutor<BillDetail> {
}
