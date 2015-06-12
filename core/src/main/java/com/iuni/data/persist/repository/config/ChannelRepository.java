package com.iuni.data.persist.repository.config;

import com.iuni.data.persist.domain.config.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ChannelRepository extends JpaRepository<Channel, Long>, QueryDslPredicateExecutor<Channel> {

    List<Channel> findByStatusAndCancelFlag(int status, int cancelFlag);

    Channel findByCode(String channelCode);

}
