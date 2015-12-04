package com.iuni.data.webapp.controller.config;

import com.iuni.data.common.ConfigConstants;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.webapp.annotation.FormModel;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.response.StatusResponse;
import com.iuni.data.webapp.service.config.PageTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/config/pagetag")
public class PageTagConfigController {

    private static final Logger logger = LoggerFactory.getLogger(PageTagConfigController.class);

    @Autowired
    private PageTagService pageTagService;

    /**
     * 返回RTAG配置页面，分页显示RTAG
     * @param currentPage
     * @param pageSize
     * @param rTag
     * @return
     */
    @RequestMapping
    public ModelAndView listPageTag(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                    @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                    @FormModel("rTag") RTag rTag) {
        List<RTag> rTagList;
        rTag.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        rTagList = pageTagService.listPageTagByPage(page, rTag);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_pagetag.getPath());
        modelAndView.addObject("rTagList", rTagList);
        modelAndView.addObject("page", page);
//        modelAndView.addObject("rTag", rTag);
        return modelAndView;
    }

    /**
     * 返回添加/编辑页面，新建一个RTag对象
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addPageTag() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_pagetag_edit.getPath());
        RTag rTag = new RTag();
        modelAndView.addObject("rTag", rTag);
        return modelAndView;
    }

    /**
     * 返回添加/编辑页面，查找RTag对象返回页面
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editPageTag(@RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_pagetag_edit.getPath());
        RTag rTag = pageTagService.getPageTagById(id);
        modelAndView.addObject("rTag", rTag);
        return modelAndView;
    }

    /**
     * 保存
     * @param rTag
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public StatusResponse savePageTag(@FormModel("rTag") RTag rTag) {
        if (rTag.getId() == 0) {
            if (pageTagService.addPageTag(rTag))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "添加成功", StatusResponse.CALL_BACK_TYPE_CLOSE);
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "添加失败");
        } else {
            if (pageTagService.updatePageTag(rTag))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "修改成功", StatusResponse.CALL_BACK_TYPE_CLOSE);
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "修改失败");
        }
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public StatusResponse deletePageTag(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("delete page tags. ids: {}", ids);
        if (!pageTagService.deletePageTag(ids))
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "删除失败");
        return new StatusResponse(StatusResponse.STATUS_CODE_OK, "删除成功");
    }

    /* ================= */
    /* getter and setter */
    /* ================= */

}
