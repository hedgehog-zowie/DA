package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.persist.domain.ConfigConstants;
import com.iuni.data.persist.domain.config.*;
import com.iuni.data.persist.domain.config.BuriedGroup;
import com.iuni.data.persist.repository.config.BuriedGroupRepository;
import com.iuni.data.persist.repository.config.BuriedPointRepository;
import com.iuni.data.persist.repository.config.BuriedRelationRepository;
import com.iuni.data.webapp.service.config.BuriedGroupService;
import com.iuni.data.webapp.sso.service.AccountService;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class BuriedGroupServiceImpl implements BuriedGroupService {

    private static final Logger logger = LoggerFactory.getLogger(BuriedGroupService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private BuriedPointRepository buriedPointRepository;
    @Autowired
    private BuriedGroupRepository buriedGroupRepository;
    @Autowired
    private BuriedRelationRepository buriedRelationRepository;

    @Override
    public BuriedGroup getById(Long id) {
        BuriedGroup buriedGroup = null;
        try {
            buriedGroup = buriedGroupRepository.findOne(id);
            checkBuriedRelations(buriedGroup);
        } catch (Exception e) {
            logger.error("get buriedGroup by id error. msg:" + e.getLocalizedMessage());
        }
        if (buriedGroup == null)
            buriedGroup = new BuriedGroup();
        return buriedGroup;
    }

    @Override
    public List<BuriedGroup> listBuriedGroup(BuriedGroup buriedGroup) {
        List<BuriedGroup> buriedGroupList = new ArrayList<>();
        QBuriedGroup qBuriedGroup = QBuriedGroup.buriedGroup;
        BooleanExpression booleanExpression = generateExpression(qBuriedGroup, buriedGroup);
        Iterable<BuriedGroup> iterable = buriedGroupRepository.findAll(booleanExpression);
        Iterator<BuriedGroup> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            BuriedGroup nBuriedGroup = iterator.next();
            checkBuriedRelations(nBuriedGroup);
            buriedGroupList.add(nBuriedGroup);
        }
        Collections.sort(buriedGroupList);
        return buriedGroupList;
    }

    @Override
    public boolean addBuriedGroup(BuriedGroup buriedGroup) {
        buriedGroup.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
        return saveBuriedGroup(buriedGroup);
    }

    @Override
    public boolean updateBuriedGroup(BuriedGroup buriedGroup) {
        BuriedGroup oldBuriedGroup = getById(buriedGroup.getId());
        if (oldBuriedGroup != null) {
            buriedGroup.setCreateBy(oldBuriedGroup.getCreateBy());
            buriedGroup.setCreateDate(oldBuriedGroup.getCreateDate());
            buriedGroup.setStatus(oldBuriedGroup.getStatus());
            buriedGroup.setBuriedRelations(oldBuriedGroup.getBuriedRelations());
            buriedGroup.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
        }
        return saveBuriedGroup(buriedGroup);
    }

    @Override
    public boolean deleteBuriedGroup(String ids) {
        List<BuriedGroup> buriedGroupList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                BuriedGroup buriedGroup = getById(Long.parseLong(id));
                if (buriedGroup != null) {
                    buriedGroup.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    buriedGroupList.add(buriedGroup);
                }
            }
        } catch (Exception e) {
            logger.error("logic delete buriedGroup error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveBuriedGroup(buriedGroupList);
    }

    @Override
    public boolean enableBuriedGroup(String ids) {
        List<BuriedGroup> buriedGroupList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                BuriedGroup buriedGroup = getById(Long.parseLong(id));
                if (buriedGroup != null) {
                    buriedGroup.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
                    buriedGroup.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
                    buriedGroupList.add(buriedGroup);
                }
            }
        } catch (Exception e) {
            logger.error("enable buriedGroup error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveBuriedGroup(buriedGroupList);
    }

    @Override
    public boolean disableBuriedGroup(String ids) {
        List<BuriedGroup> buriedGroupList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                BuriedGroup buriedGroup = getById(Long.parseLong(id));
                if (buriedGroup != null) {
                    buriedGroup.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
                    buriedGroup.setStatus(ConfigConstants.STATUS_FLAG_INVALID);
                    buriedGroupList.add(buriedGroup);
                }
            }
        } catch (Exception e) {
            logger.error("enable buriedGroup error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return saveBuriedGroup(buriedGroupList);
    }

    private boolean saveBuriedGroup(BuriedGroup buriedGroup) {
        try {
            // save group
            BuriedGroup savedGroup = buriedGroupRepository.save(buriedGroup);
            // update old relations to invalid
            if (buriedGroup.getBuriedRelations() != null && buriedGroup.getBuriedRelations().size() != 0) {
                List<BuriedRelation> oldBuriedRelations = new ArrayList<>();
                for (BuriedRelation buriedRelation : buriedGroup.getBuriedRelations()) {
                    buriedRelation.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    oldBuriedRelations.add(buriedRelation);
                }
                buriedRelationRepository.save(oldBuriedRelations);
            }
            // save relations
            List<BuriedRelation> buriedRelationList = new ArrayList<>();
//            for (Long buriedPointId : buriedGroup.getBuriedPoints()) {
            int i = 0;
            for (Long buriedPointId : buriedGroup.getSortedBuriedPoints()) {
                BuriedRelation buriedRelation = new BuriedRelation();
                buriedRelation.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
                buriedRelation.setBuriedPoint(buriedPointRepository.findOne(buriedPointId));
                buriedRelation.setBuriedGroup(savedGroup);
                buriedRelation.setIndex(i++);
                buriedRelationList.add(buriedRelation);
            }
            buriedRelationRepository.save(buriedRelationList);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    private boolean saveBuriedGroup(List<BuriedGroup> buriedGroupList) {
        try {
            buriedGroupRepository.save(buriedGroupList);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    /**
     * generate condition expression
     *
     * @param qBuriedGroup
     * @param buriedGroup
     * @return
     */
    private BooleanExpression generateExpression(QBuriedGroup qBuriedGroup, BuriedGroup buriedGroup) {
        BooleanExpression booleanExpression = null;
        if (buriedGroup.getCancelFlag() != null) {
            BooleanExpression cancelBooleanExpression = qBuriedGroup.cancelFlag.eq(buriedGroup.getCancelFlag());
            booleanExpression = (booleanExpression == null ? cancelBooleanExpression : booleanExpression.and(cancelBooleanExpression));
        }
        if (buriedGroup.getStatus() != null) {
            BooleanExpression statusBooleanExpression = qBuriedGroup.status.eq(buriedGroup.getStatus());
            booleanExpression = (booleanExpression == null ? statusBooleanExpression : booleanExpression.and(statusBooleanExpression));
        }
        if (StringUtils.isNotBlank(buriedGroup.getName())) {
            BooleanExpression nameBooleanExpression = qBuriedGroup.name.like("%" + buriedGroup.getName() + "%");
            booleanExpression = (nameBooleanExpression == null ? nameBooleanExpression : nameBooleanExpression.and(nameBooleanExpression));
        }
        return booleanExpression;
    }

    /**
     * 检查关系是否有效，剔除无效关系
     *
     * @param buriedGroup
     */
    private void checkBuriedRelations(BuriedGroup buriedGroup) {
        if (buriedGroup.getBuriedRelations() == null)
            return;
        List<BuriedRelation> validRelations = new ArrayList();
        for (BuriedRelation buriedRelation : buriedGroup.getBuriedRelations()) {
            if (!(buriedRelation.getCancelFlag() == 0 && buriedRelation.getStatus() == 1))
                continue;
            validRelations.add(buriedRelation);
        }
        Collections.sort(validRelations);
        buriedGroup.setBuriedRelations(validRelations);
        Long[] buriedPoints = new Long[buriedGroup.getBuriedRelations().size()];
        for (int i = 0; i < buriedGroup.getBuriedRelations().size(); i++)
            buriedPoints[i] = buriedGroup.getBuriedRelations().get(i).getBuriedPoint().getId();
        buriedGroup.setBuriedPoints(buriedPoints);
        buriedGroup.setSortedBuriedPoints(buriedPoints);
    }
}
