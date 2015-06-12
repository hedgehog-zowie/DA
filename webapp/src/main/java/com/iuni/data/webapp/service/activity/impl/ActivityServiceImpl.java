package com.iuni.data.webapp.service.activity.impl;

import com.iuni.data.common.UrlUtils;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.persist.domain.webkpi.ClickWebKpi;
import com.iuni.data.persist.domain.webkpi.PageWebKpi;
import com.iuni.data.persist.domain.webkpi.QClickWebKpi;
import com.iuni.data.persist.domain.webkpi.QPageWebKpi;
import com.iuni.data.persist.repository.config.PageTagRepository;
import com.iuni.data.persist.repository.webkpi.ClickWebKpiRepository;
import com.iuni.data.persist.repository.webkpi.PageWebKpiRepository;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.dto.ActivityKpi;
import com.iuni.data.webapp.service.activity.ActivityService;
import com.iuni.data.webapp.service.config.PageTagService;
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

import java.util.*;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private PageWebKpiRepository pageWebKpiRepository;
    @Autowired
    private ClickWebKpiRepository clickWebKpiRepository;
    @Autowired
    private PageTagService pageTagService;

    @Override
    public List<ActivityKpi> findActivityKpi(String url, String channelCode, Date startDate, Date endDate, String tType, PageVO page) {
        Map<String, ActivityKpi> activeKpiMap = new HashMap<>();
        if (!StringUtils.isBlank(url)) {
            url = UrlUtils.completeUrl(url);
            List<PageWebKpi> pageWebKpiList = findActivityPageWebKpi(url, channelCode, startDate, endDate, tType, page);
            if (pageWebKpiList.size() != 0) {
                for (PageWebKpi pageWebKpi : pageWebKpiList) {
                    ActivityKpi activeKpi = new ActivityKpi();
                    activeKpi.setPv(pageWebKpi.getPv());
                    activeKpi.setUv(pageWebKpi.getUv());
                    activeKpi.setDate(pageWebKpi.getTime());
                    String key = pageWebKpi.getPage() + pageWebKpi.getChannel().getCode() + pageWebKpi.getTtype() + pageWebKpi.getTime();
                    activeKpiMap.put(key, activeKpi);
                }

                RTag rTag = new RTag();
                rTag.setInfo(url);
                rTag.setCancelFlag(0);
                rTag.setStatus(1);
                List<RTag> rTagList = pageTagService.listPageTag(rTag);
                List<ClickWebKpi> clickWebKpiList = findActivityClickWebKpi(url, channelCode, startDate, endDate, tType);
                for (ClickWebKpi clickWebKpi : clickWebKpiList) {
                    String key = clickWebKpi.getRtag().getInfo() + clickWebKpi.getChannel().getCode() + clickWebKpi.getTtype() + clickWebKpi.getTime();
                    ActivityKpi activeKpi = activeKpiMap.get(key);
                    if (activeKpi == null)
                        continue;
                    Map<String, Integer> countMap = activeKpi.getCountMap();
                    if (countMap == null) {
                        countMap = new HashMap<>();
                        for (RTag tag : rTagList)
                            countMap.put(tag.getName(), 0);
                    }
                    countMap.put(clickWebKpi.getRtag().getName(), countMap.get(clickWebKpi.getRtag().getName()) + clickWebKpi.getCount());
                    activeKpi.setCountMap(countMap);
                }
            }
        }
        List<ActivityKpi> activityKpiList = new ArrayList<>();
        if (activeKpiMap != null && activeKpiMap.size() != 0) {
            for (Map.Entry<String, ActivityKpi> entry : activeKpiMap.entrySet())
                activityKpiList.add(entry.getValue());
        }
        return activityKpiList;
    }

    private List<PageWebKpi> findActivityPageWebKpi(String url, String channelCode, Date startDate, Date endDate, String tType, PageVO page) {
        List<PageWebKpi> pageWebKpiList = new ArrayList<>();
        QPageWebKpi qPageWebKpi = QPageWebKpi.pageWebKpi;
        BooleanExpression booleanExpression = generateExpression(qPageWebKpi, url, channelCode, startDate, endDate, tType);
        try {
            Sort sort = new Sort(Sort.Direction.ASC, new String[]{"time"});
            Pageable pageable = new PageRequest(page.getCurrentPage() - 1, page.getPageSize(), sort);
            Page resultPage;
            if (booleanExpression == null)
                resultPage = pageWebKpiRepository.findAll(pageable);
            else
                resultPage = pageWebKpiRepository.findAll(booleanExpression, pageable);
            page.setPage(resultPage);
            pageWebKpiList = resultPage.getContent();
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
        }
        if (pageWebKpiList == null)
            pageWebKpiList = new ArrayList<>();
        return pageWebKpiList;
    }

    private List<ClickWebKpi> findActivityClickWebKpi(String url, String channelCode, Date startDate, Date endDate, String tType) {
        List<ClickWebKpi> clickWebKpiList;
        QClickWebKpi qClickWebKpi = QClickWebKpi.clickWebKpi;
        BooleanExpression booleanExpression = generateExpression(qClickWebKpi, url, channelCode, startDate, endDate, tType);
        if (booleanExpression == null)
            clickWebKpiList = clickWebKpiRepository.findAll();
        else
            clickWebKpiList = (List<ClickWebKpi>) clickWebKpiRepository.findAll(booleanExpression);
        if (clickWebKpiList == null)
            clickWebKpiList = new ArrayList<>();
        return clickWebKpiList;
    }

    private BooleanExpression generateExpression(QPageWebKpi qPageWebKpi, String url, String channelCode, Date startDate, Date endDate, String tType) {
        BooleanExpression booleanExpression = null;
        if (!StringUtils.isBlank(url)) {
            BooleanExpression urlBooleanExpression = qPageWebKpi.page.eq(url);
            booleanExpression = (booleanExpression == null ? urlBooleanExpression : booleanExpression.and(urlBooleanExpression));
        }
        if (!StringUtils.isBlank(channelCode)) {
            BooleanExpression channelBooleanExpression = qPageWebKpi.channel.code.eq(channelCode);
            booleanExpression = (booleanExpression == null ? channelBooleanExpression : booleanExpression.and(channelBooleanExpression));
        }
        if (startDate != null) {
            BooleanExpression greaterThanStartBooleanExpression = qPageWebKpi.time.gt(startDate);
            BooleanExpression equalStartBooleanExpression = qPageWebKpi.time.eq(startDate);
            BooleanExpression timeBooleanExpression = greaterThanStartBooleanExpression.or(equalStartBooleanExpression);
            booleanExpression = (booleanExpression == null ? timeBooleanExpression : booleanExpression.and(timeBooleanExpression));
        }
        if (endDate != null) {
            BooleanExpression lessThanEndBooleanExpression = qPageWebKpi.time.lt(endDate);
            BooleanExpression equalStartBooleanExpression = qPageWebKpi.time.eq(endDate);
            BooleanExpression timeBooleanExpression = lessThanEndBooleanExpression.or(equalStartBooleanExpression);
            booleanExpression = (booleanExpression == null ? timeBooleanExpression : booleanExpression.and(timeBooleanExpression));
        }
        if (!StringUtils.isBlank(tType)) {
            BooleanExpression tTypeBooleanExpression = qPageWebKpi.ttype.eq(tType);
            booleanExpression = (booleanExpression == null ? tTypeBooleanExpression : booleanExpression.and(tTypeBooleanExpression));
        }
        return booleanExpression;
    }

    private BooleanExpression generateExpression(QClickWebKpi qClickWebKpi, String url, String channelCode, Date startDate, Date endDate, String tType) {
        BooleanExpression booleanExpression = null;
        if (!StringUtils.isBlank(url)) {
            BooleanExpression urlBooleanExpression = qClickWebKpi.rtag.info.eq(url);
            booleanExpression = (booleanExpression == null ? urlBooleanExpression : booleanExpression.and(urlBooleanExpression));
        }
        if (!StringUtils.isBlank(channelCode)) {
            BooleanExpression channelBooleanExpression = qClickWebKpi.channel.code.eq(channelCode);
            booleanExpression = (booleanExpression == null ? channelBooleanExpression : booleanExpression.and(channelBooleanExpression));
        }
        if (startDate != null) {
            BooleanExpression greaterThanStartBooleanExpression = qClickWebKpi.time.gt(startDate);
            BooleanExpression equalStartBooleanExpression = qClickWebKpi.time.eq(startDate);
            BooleanExpression timeBooleanExpression = greaterThanStartBooleanExpression.or(equalStartBooleanExpression);
            booleanExpression = (booleanExpression == null ? timeBooleanExpression : booleanExpression.and(timeBooleanExpression));
        }
        if (endDate != null) {
            BooleanExpression lessThanEndBooleanExpression = qClickWebKpi.time.lt(endDate);
            BooleanExpression equalStartBooleanExpression = qClickWebKpi.time.eq(endDate);
            BooleanExpression timeBooleanExpression = lessThanEndBooleanExpression.or(equalStartBooleanExpression);
            booleanExpression = (booleanExpression == null ? timeBooleanExpression : booleanExpression.and(timeBooleanExpression));
        }
        if (!StringUtils.isBlank(tType)) {
            BooleanExpression tTypeBooleanExpression = qClickWebKpi.ttype.eq(tType);
            booleanExpression = (booleanExpression == null ? tTypeBooleanExpression : booleanExpression.and(tTypeBooleanExpression));
        }
        return booleanExpression;
    }

}
