package com.iuni.data.persist.repository.taobao;

import com.iuni.data.persist.domain.taobao.TradeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface TradeRecordRepository extends JpaRepository<TradeRecord, Long>, QueryDslPredicateExecutor<TradeRecord> {

    @Modifying
    @Query("delete from TradeRecord where alipayOrderNo = :alipayOrderNo and createTime = :createTime")
    void deleteByAlipayOrderNo(@Param("alipayOrderNo") String alipayOrderNo, @Param("createTime") String createTime);

}
