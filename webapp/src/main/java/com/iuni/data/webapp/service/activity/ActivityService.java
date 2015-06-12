package com.iuni.data.webapp.service.activity;

import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.dto.ActivityKpi;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface ActivityService {

    List<ActivityKpi> findActivityKpi(String url, String channelCode, Date startDate, Date endDate, String tType, PageVO page);

}
