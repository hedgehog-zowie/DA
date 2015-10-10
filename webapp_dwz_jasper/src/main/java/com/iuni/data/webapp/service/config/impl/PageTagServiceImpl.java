package com.iuni.data.webapp.service.config.impl;

import com.iuni.data.persist.domain.config.QRTag;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.persist.domain.config.RTagType;
import com.iuni.data.persist.repository.config.PageTagRepository;
import com.iuni.data.persist.repository.config.PageTagTypeRepository;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.service.config.PageTagService;
import com.iuni.data.webapp.sso.service.AccountService;
import com.mysema.query.types.expr.BooleanExpression;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class PageTagServiceImpl implements PageTagService {

    private static final Logger logger = LoggerFactory.getLogger(PageTagService.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private PageTagRepository pageTagRepository;
    @Autowired
    private PageTagTypeRepository pageTagTypeRepository;

    @Override
    public RTag getPageTagById(Long id) {
        RTag rTag = null;
        try {
            rTag = pageTagRepository.findOne(id);
        } catch (Exception e) {
            logger.error("get rTag by id error. msg:" + e.getLocalizedMessage());
        }
        return rTag;
    }

    @Override
    public RTagType getPageTagTypeById(Long id) {
        RTagType rTagType = null;
        try {
            rTagType = pageTagTypeRepository.findOne(id);
        } catch (Exception e) {
            logger.error("get rTagType by id error. msg:" + e.getLocalizedMessage());
        }
        return rTagType;
    }

    @Override
    public List<RTag> listPageTag(final RTag rTag) {
        List<RTag> rTagList = new ArrayList<>();
        QRTag qrTag = QRTag.rTag;
        BooleanExpression booleanExpression = generateExpression(qrTag, rTag);
        Iterable<RTag> iterable = pageTagRepository.findAll(booleanExpression);
        Iterator<RTag> iterator = iterable.iterator();
        while (iterator.hasNext())
            rTagList.add(iterator.next());
        if (rTag == null)
            return new ArrayList<>();
        return rTagList;
    }

    @Override
    public List<RTag> listPageTagByPage(PageVO page, final RTag rTag) {
        List<RTag> rTagList = null;
        QRTag qrTag = QRTag.rTag;
        BooleanExpression booleanExpression = generateExpression(qrTag, rTag);
        try {
            Sort sort = new Sort(Sort.Direction.DESC, new String[]{"updateDate"});
            Pageable pageable = new PageRequest(page.getCurrentPage() - 1, page.getPageSize(), sort);
            Page resultPage;
            if (booleanExpression == null)
                resultPage = pageTagRepository.findAll(pageable);
            else
                resultPage = pageTagRepository.findAll(booleanExpression, pageable);
            page.setPage(resultPage);
            rTagList = resultPage.getContent();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        if (rTagList == null)
            rTagList = new ArrayList<>();
        return rTagList;
    }

    /**
     * generate condition expression
     *
     * @param qrTag
     * @param rTag
     * @return
     */
    private BooleanExpression generateExpression(QRTag qrTag, RTag rTag) {
        BooleanExpression booleanExpression = null;
        if (rTag.getCancelFlag() != null) {
            BooleanExpression cancelBooleanExpression = qrTag.cancelFlag.eq(rTag.getCancelFlag());
            booleanExpression = (booleanExpression == null ? cancelBooleanExpression : booleanExpression.and(cancelBooleanExpression));
        }
        if (rTag.getStatus() != null) {
            BooleanExpression statusBooleanExpression = qrTag.status.eq(rTag.getStatus());
            booleanExpression = (booleanExpression == null ? statusBooleanExpression : booleanExpression.and(statusBooleanExpression));
        }
        if (StringUtils.isNotBlank(rTag.getRtag())) {
            BooleanExpression rTagBooleanExpression = qrTag.rtag.like("%" + rTag.getRtag() + "%");
            booleanExpression = (booleanExpression == null ? rTagBooleanExpression : booleanExpression.and(rTagBooleanExpression));
        }
        if (rTag.getrTagType() != null && rTag.getrTagType().getId() != 0) {
            BooleanExpression typeBooleanExpression = qrTag.rTagType.id.eq(rTag.getrTagType().getId());
            booleanExpression = (booleanExpression == null ? typeBooleanExpression : booleanExpression.and(typeBooleanExpression));
        }
        if (!StringUtils.isBlank(rTag.getInfo())) {
            BooleanExpression infoBooleanExpression = qrTag.info.eq(rTag.getInfo());
            booleanExpression = (booleanExpression == null ? infoBooleanExpression : booleanExpression.and(infoBooleanExpression));
        }
        return booleanExpression;
    }

    @Override
    public boolean addPageTag(RTag rTag) {
        rTag.setBasicInfoForCreate(accountService.getCurrentUser().getLoginName());
        return savePageTag(rTag);
    }

    @Override
    public boolean updatePageTag(RTag rTag) {
        RTag oldRTag = getPageTagById(rTag.getId());
        if (oldRTag != null) {
            rTag.setrTagType(oldRTag.getrTagType());
            rTag.setCreateDate(oldRTag.getCreateDate());
            rTag.setCreateBy(oldRTag.getCreateBy());
            rTag.setBasicInfoForUpdate(accountService.getCurrentUser().getLoginName());
        }
        return savePageTag(rTag);
    }

    private boolean savePageTag(RTag rTag) {
        try {
            pageTagRepository.save(rTag);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletePageTag(String ids) {
        List<RTag> rTagList = new ArrayList<>();
        try {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                if (!StringUtils.isNumeric(id))
                    continue;
                RTag rTag = pageTagRepository.findOne(Long.parseLong(id));
                if (rTag != null) {
                    // set cancel flag to 1, logical delete it.
                    rTag.setBasicInfoForCancel(accountService.getCurrentUser().getLoginName());
                    rTagList.add(rTag);
                }
            }
        } catch (Exception e) {
            logger.error("logic delete rtag error. msg: {}", e.getLocalizedMessage());
            return false;
        }
        return savePageTag(rTagList);
    }

    private boolean savePageTag(List<RTag> rTagList) {
        try {
            pageTagRepository.save(rTagList);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            return false;
        }
        return true;
    }

}
