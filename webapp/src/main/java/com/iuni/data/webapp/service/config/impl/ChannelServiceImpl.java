package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.persist.domain.ConfigConstants;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.ChannelType;
import com.iuni.data.persist.domain.config.QChannel;
import com.iuni.data.persist.domain.config.QChannelType;
import com.iuni.data.persist.repository.config.ChannelRepository;
import com.iuni.data.persist.repository.config.ChannelTypeRepository;
import com.iuni.data.webapp.service.config.ChannelService;
import com.iuni.data.webapp.sso.service.AccountService;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    private static final Logger logger = LoggerFactory.getLogger(ChannelService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public Channel getById(Long id) {
        Channel channel = null;
        try {
            channel = channelRepository.findOne(id);
//            channelType.setChannelType(channelType.getCode().substring(0, 2));
            channel.setChannelSerial(channel.getCode().substring(2, 4));
            channel.setActiveDate(channel.getCode().substring(4));
        } catch (Exception e) {
            logger.error("get channel by id error. msg:" + e.getLocalizedMessage());
        }
        if (channel == null)
            channel = new Channel();
        return channel;
    }

    @Override
    public Channel getByCode(String code) {
        Channel channel = null;
        try {
            channel = channelRepository.findByCode(code);
        } catch (Exception e) {
            logger.error("get channel by code error. msg:" + e.getLocalizedMessage());
        }
        return channel;
    }

    @Override
    public List<Channel> listChannel(Channel channel) {
        List<Channel> channelList = new ArrayList<>();
        QChannel qchannel = QChannel.channel;
        BooleanExpression booleanExpression = generateExpression(qchannel, channel);
        Iterable<Channel> iterable = channelRepository.findAll(booleanExpression);
        Iterator<Channel> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Channel nChannel = iterator.next();
            String code = nChannel.getCode();
            if (code.length() == Channel.CODE_LENGTH) {
//                nChannel.setChannelType(code.substring(0, 2));
                nChannel.setChannelSerial(code.substring(2, 4));
                nChannel.setActiveDate(code.substring(4));
            }
            channelList.add(nChannel);
        }
        return channelList;
    }

    @Override
    public boolean addChannel(Channel channel) {
        channel.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
        return saveChannel(channel);
    }

    @Override
    public boolean updateChannel(Channel channel) {
        Channel oldChannel = getById(channel.getId());
        if (oldChannel != null) {
            channel.setCreateBy(oldChannel.getCreateBy());
            channel.setCreateDate(oldChannel.getCreateDate());
            channel.setStatus(oldChannel.getStatus());
            channel.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
        }
        return saveChannel(channel);
    }

    @Override
    public boolean deleteChannel(String ids) {
        List<Channel> channelList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                Channel channel = getById(Long.parseLong(id));
                if (channel != null) {
                    channel.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    channelList.add(channel);
                }
            }
        } catch (Exception e) {
            logger.error("logic delete channel error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveChannel(channelList);
    }

    @Override
    public boolean enableChannel(String ids){
        List<Channel> channelList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                Channel channel = getById(Long.parseLong(id));
                if (channel != null) {
                    channel.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
                    channel.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
                    channelList.add(channel);
                }
            }
        } catch (Exception e) {
            logger.error("enable channel error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveChannel(channelList);
    }

    @Override
    public boolean disableChannel(String ids){
        List<Channel> channelList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                Channel channel = getById(Long.parseLong(id));
                if (channel != null) {
                    channel.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
                    channel.setStatus(ConfigConstants.STATUS_FLAG_INVALID);
                    channelList.add(channel);
                }
            }
        } catch (Exception e) {
            logger.error("disable channel error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveChannel(channelList);
    }

    private boolean saveChannel(Channel channel) {
        try {
            channelRepository.save(channel);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private boolean saveChannel(List<Channel> channelList) {
        try {
            channelRepository.save(channelList);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * generate condition expression
     *
     * @param qChannel
     * @param channel
     * @return
     */
    private BooleanExpression generateExpression(QChannel qChannel, Channel channel) {
        BooleanExpression booleanExpression = null;
        if (channel.getCancelFlag() != null) {
            BooleanExpression cancelBooleanExpression = qChannel.cancelFlag.eq(channel.getCancelFlag());
            booleanExpression = (booleanExpression == null ? cancelBooleanExpression : booleanExpression.and(cancelBooleanExpression));
        }
        if (channel.getStatus() != null) {
            BooleanExpression statusBooleanExpression = qChannel.status.eq(channel.getStatus());
            booleanExpression = (booleanExpression == null ? statusBooleanExpression : booleanExpression.and(statusBooleanExpression));
        }
        if (StringUtils.isNotBlank(channel.getCode())) {
            BooleanExpression codeBooleanExpression = qChannel.code.like("%" + channel.getCode() + "%");
            booleanExpression = (booleanExpression == null ? codeBooleanExpression : booleanExpression.and(codeBooleanExpression));
        }
        if (StringUtils.isNotBlank(channel.getName())) {
            BooleanExpression nameBooleanExpression = qChannel.name.like("%" + channel.getName() + "%");
            booleanExpression = (booleanExpression == null ? nameBooleanExpression : booleanExpression.and(nameBooleanExpression));
        }
        return booleanExpression;
    }

}
