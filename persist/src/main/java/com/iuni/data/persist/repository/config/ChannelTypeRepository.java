package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.ChannelType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ChannelTypeRepository extends JpaRepository<ChannelType, Long>, QueryDslPredicateExecutor<ChannelType> {

    List<ChannelType> findByStatusAndCancelFlag(int status, int cancelFlag);

}
