package com.iuni.data.webapp.service.config;

import com.iuni.data.persist.domain.config.QRTag;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.persist.domain.config.RTagType;
import com.iuni.data.webapp.common.PageVO;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
public interface PageTagService {

    RTag getPageTagById(Long id);

    List<RTag> listPageTag(final RTag rTag);

    /**
     * list all setBasicInfoForCreate pageTags by page
     * @param rTag
     * @return
     */
    List<RTag> listPageTagByPage(PageVO page, final RTag rTag);

    boolean addPageTag(RTag rTag);

    boolean updatePageTag(RTag rTag);

    /**
     * delete pageTags by id string, ids like "1,2,3,4"
     * @param ids
     * @return
     */
    boolean deletePageTag(String ids);

    RTagType getPageTagTypeById(Long id);

}
