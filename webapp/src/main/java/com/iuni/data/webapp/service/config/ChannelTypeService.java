package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.ChannelType;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ChannelTypeService {

    ChannelType getById(Long id);

    List<ChannelType> listChannelType(final ChannelType channelType);

    boolean addChannelType(ChannelType channelType);

    boolean updateChannelType(ChannelType channelType);

    boolean deleteChannelType(String ids);
}
