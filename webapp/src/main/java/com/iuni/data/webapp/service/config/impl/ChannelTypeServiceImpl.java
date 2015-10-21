package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.ChannelType;
import com.iuni.data.persist.domain.config.QChannel;
import com.iuni.data.persist.domain.config.QChannelType;
import com.iuni.data.persist.repository.config.ChannelRepository;
import com.iuni.data.persist.repository.config.ChannelTypeRepository;
import com.iuni.data.webapp.service.config.ChannelService;
import com.iuni.data.webapp.service.config.ChannelTypeService;
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
public class ChannelTypeServiceImpl implements ChannelTypeService {

    private static final Logger logger = LoggerFactory.getLogger(ChannelService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private ChannelTypeRepository channelTypeRepository;

    @Override
    public ChannelType getById(Long id) {
        ChannelType channelType = null;
        try {
            channelType = channelTypeRepository.findOne(id);
        } catch (Exception e) {
            logger.error("get channelType by id error. msg:" + e.getLocalizedMessage());
        }
        if (channelType == null)
            channelType = new ChannelType();
        return channelType;
    }

    @Override
    public List<ChannelType> listChannelType(ChannelType channelType) {
        List<ChannelType> channelTypeList = new ArrayList<>();
        QChannelType qChannelType = QChannelType.channelType;
        BooleanExpression booleanExpression = generateExpression(qChannelType, channelType);
        Iterable<ChannelType> iterable = channelTypeRepository.findAll(booleanExpression);
        Iterator<ChannelType> iterator = iterable.iterator();
        while (iterator.hasNext())
            channelTypeList.add(iterator.next());
        return channelTypeList;
    }

    @Override
    public boolean addChannelType(ChannelType channelType) {
        channelType.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
        return saveChannelType(channelType);
    }

    @Override
    public boolean updateChannelType(ChannelType channelType) {
        ChannelType oldChannelType = getById(channelType.getId());
        if (oldChannelType != null) {
            channelType.setCreateBy(oldChannelType.getCreateBy());
            channelType.setCreateDate(oldChannelType.getCreateDate());
            channelType.setStatus(oldChannelType.getStatus());
            channelType.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
        }
        return saveChannelType(channelType);
    }

    @Override
    public boolean deleteChannelType(String ids) {
        List<ChannelType> channelTypeList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                ChannelType channelType = getById(Long.parseLong(id));
                if (channelType != null) {
                    channelType.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    channelTypeList.add(channelType);
                }
            }
        } catch (Exception e) {
            logger.error("logic delete channelType error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveChannelType(channelTypeList);
    }

    private boolean saveChannelType(ChannelType channelType) {
        try {
            channelTypeRepository.save(channelType);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private boolean saveChannelType(List<ChannelType> channelTypeList) {
        try {
            channelTypeRepository.save(channelTypeList);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * generate condition expression
     *
     * @param qChannelType
     * @param channelType
     * @return
     */
    private BooleanExpression generateExpression(QChannelType qChannelType, ChannelType channelType) {
        BooleanExpression booleanExpression = null;
        if (channelType.getCancelFlag() != null) {
            BooleanExpression cancelBooleanExpression = qChannelType.cancelFlag.eq(channelType.getCancelFlag());
            booleanExpression = (booleanExpression == null ? cancelBooleanExpression : booleanExpression.and(cancelBooleanExpression));
        }
        if (channelType.getStatus() != null) {
            BooleanExpression statusBooleanExpression = qChannelType.status.eq(channelType.getStatus());
            booleanExpression = (booleanExpression == null ? statusBooleanExpression : booleanExpression.and(statusBooleanExpression));
        }
        if (StringUtils.isNotBlank(channelType.getCode())) {
            BooleanExpression codeBooleanExpression = qChannelType.code.like("%" + channelType.getCode() + "%");
            booleanExpression = (booleanExpression == null ? codeBooleanExpression : booleanExpression.and(codeBooleanExpression));
        }
        if (StringUtils.isNotBlank(channelType.getName())) {
            BooleanExpression nameBooleanExpression = qChannelType.name.like("%" + channelType.getName() + "%");
            booleanExpression = (booleanExpression == null ? nameBooleanExpression : booleanExpression.and(nameBooleanExpression));
        }
        return booleanExpression;
    }
}
