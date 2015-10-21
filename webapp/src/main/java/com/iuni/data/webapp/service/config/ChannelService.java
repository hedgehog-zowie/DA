package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.ChannelType;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ChannelService {

    Channel getById(Long id);

    Channel getByCode(String code);

    List<Channel> listChannel(final Channel channel);

    boolean addChannel(Channel channel);

    boolean updateChannel(Channel channel);

    boolean deleteChannel(String ids);

    boolean enableChannel(String ids);

    boolean disableChannel(String ids);

}
