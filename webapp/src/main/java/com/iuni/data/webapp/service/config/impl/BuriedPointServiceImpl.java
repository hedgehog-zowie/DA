package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.persist.domain.ConfigConstants;
import com.iuni.data.persist.domain.config.BuriedPoint;
import com.iuni.data.persist.domain.config.QBuriedPoint;
import com.iuni.data.persist.repository.config.BuriedPointRepository;
import com.iuni.data.webapp.service.config.BuriedPointService;
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
public class BuriedPointServiceImpl implements BuriedPointService {

    private static final Logger logger = LoggerFactory.getLogger(BuriedPointService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private BuriedPointRepository buriedPointRepository;

    @Override
    public BuriedPoint getById(Long id) {
        BuriedPoint buriedPoint = null;
        try {
            buriedPoint = buriedPointRepository.findOne(id);
        } catch (Exception e) {
            logger.error("get buriedPoint by id error. msg:" + e.getLocalizedMessage());
        }
        if (buriedPoint == null)
            buriedPoint = new BuriedPoint();
        return buriedPoint;
    }

    @Override
    public List<BuriedPoint> listBuriedPoint(BuriedPoint buriedPoint) {
        List<BuriedPoint> buriedPointList = new ArrayList<>();
        QBuriedPoint qBuriedPoint = QBuriedPoint.buriedPoint;
        BooleanExpression booleanExpression = generateExpression(qBuriedPoint, buriedPoint);
        Iterable<BuriedPoint> iterable = buriedPointRepository.findAll(booleanExpression);
        Iterator<BuriedPoint> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            BuriedPoint nBuriedPoint = iterator.next();
            buriedPointList.add(nBuriedPoint);
        }
        Collections.sort(buriedPointList);
        return buriedPointList;
    }

    @Override
    public void addBuriedPoint(BuriedPoint buriedPoint) throws Exception {
        buriedPoint.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
        saveBuriedPoint(buriedPoint);
    }

    @Override
    public void updateBuriedPoint(BuriedPoint buriedPoint) throws Exception {
        BuriedPoint oldBuriedPoint = getById(buriedPoint.getId());
        if (oldBuriedPoint != null) {
            buriedPoint.setCreateBy(oldBuriedPoint.getCreateBy());
            buriedPoint.setCreateDate(oldBuriedPoint.getCreateDate());
            buriedPoint.setStatus(oldBuriedPoint.getStatus());
            buriedPoint.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
        }
        saveBuriedPoint(buriedPoint);
    }

    @Override
    public void deleteBuriedPoint(String ids) throws Exception {
        List<BuriedPoint> buriedPointList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                BuriedPoint buriedPoint = getById(Long.parseLong(id));
                if (buriedPoint != null) {
                    buriedPoint.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    buriedPointList.add(buriedPoint);
                }
            }
            buriedPointRepository.save(buriedPointList);
        } catch (Exception e) {
            logger.error("logic delete buriedPoint error. msg: {}", e.getLocalizedMessage());
            throw new Exception(e);
        }
    }

    @Override
    public void enableBuriedPoint(String ids) throws Exception {
        List<BuriedPoint> buriedPointList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                BuriedPoint buriedPoint = getById(Long.parseLong(id));
                if (buriedPoint != null) {
                    buriedPoint.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
                    buriedPointList.add(buriedPoint);
                }
            }
            saveBuriedPoint(buriedPointList);
        } catch (Exception e) {
            logger.error("enable buriedPoint error. msg: {}", e.getLocalizedMessage());
            throw new Exception(e);
        }
    }

    @Override
    public void disableBuriedPoint(String ids) throws Exception {
        List<BuriedPoint> buriedPointList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                BuriedPoint buriedPoint = getById(Long.parseLong(id));
                if (buriedPoint != null) {
                    buriedPoint.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
                    buriedPoint.setStatus(ConfigConstants.STATUS_FLAG_INVALID);
                    buriedPointList.add(buriedPoint);
                }
            }
            buriedPointRepository.save(buriedPointList);
        } catch (Exception e) {
            logger.error("enable buriedPoint error. msg: {}", e.getLocalizedMessage());
            throw new Exception(e);
        }
    }

    private void saveBuriedPoint(BuriedPoint buriedPoint) throws Exception {
        try {
            buriedPointRepository.save(buriedPoint);
        } catch (Exception e) {
            logger.error("save buriedPoint error, msg: ", e.getLocalizedMessage());
            throw new Exception(e);
        }
    }

    private void saveBuriedPoint(List<BuriedPoint> buriedPointList) throws Exception {
        try {
            buriedPointRepository.save(buriedPointList);
        } catch (Exception e) {
            logger.error("save buriedPointList error, msg: ", e.getLocalizedMessage());
            throw new Exception(e);
        }
    }

    /**
     * generate condition expression
     *
     * @param qBuriedPoint
     * @param buriedPoint
     * @return
     */
    private BooleanExpression generateExpression(QBuriedPoint qBuriedPoint, BuriedPoint buriedPoint) {
        BooleanExpression booleanExpression = null;
        if (buriedPoint.getCancelFlag() != null) {
            BooleanExpression cancelBooleanExpression = qBuriedPoint.cancelFlag.eq(buriedPoint.getCancelFlag());
            booleanExpression = (booleanExpression == null ? cancelBooleanExpression : booleanExpression.and(cancelBooleanExpression));
        }
        if (buriedPoint.getStatus() != null) {
            BooleanExpression statusBooleanExpression = qBuriedPoint.status.eq(buriedPoint.getStatus());
            booleanExpression = (booleanExpression == null ? statusBooleanExpression : booleanExpression.and(statusBooleanExpression));
        }
        if (StringUtils.isNotBlank(buriedPoint.getPageName())) {
            BooleanExpression pageNameBooleanExpression = qBuriedPoint.pageName.like("%" + buriedPoint.getPageName() + "%");
            booleanExpression = (pageNameBooleanExpression == null ? pageNameBooleanExpression : pageNameBooleanExpression.and(pageNameBooleanExpression));
        }
        if (StringUtils.isNotBlank(buriedPoint.getPagePosition())) {
            BooleanExpression pagePositionBooleanExpression = qBuriedPoint.pagePosition.like("%" + buriedPoint.getPagePosition() + "%");
            booleanExpression = (booleanExpression == null ? pagePositionBooleanExpression : booleanExpression.and(pagePositionBooleanExpression));
        }
        if (StringUtils.isNotBlank(buriedPoint.getWebsite())) {
            BooleanExpression webSiteBooleanExpression = qBuriedPoint.pagePosition.like("%" + buriedPoint.getWebsite() + "%");
            booleanExpression = (booleanExpression == null ? webSiteBooleanExpression : booleanExpression.and(webSiteBooleanExpression));
        }
        if (StringUtils.isNotBlank(buriedPoint.getWebsiteCode())) {
            BooleanExpression webSiteCodeBooleanExpression = qBuriedPoint.pagePosition.like("%" + buriedPoint.getWebsiteCode() + "%");
            booleanExpression = (booleanExpression == null ? webSiteCodeBooleanExpression : booleanExpression.and(webSiteCodeBooleanExpression));
        }
        if (StringUtils.isNotBlank(buriedPoint.getPointFlag())) {
            BooleanExpression pointFlagBooleanExpression = qBuriedPoint.pagePosition.like("%" + buriedPoint.getPointFlag() + "%");
            booleanExpression = (booleanExpression == null ? pointFlagBooleanExpression : booleanExpression.and(pointFlagBooleanExpression));
        }
        return booleanExpression;
    }

}
