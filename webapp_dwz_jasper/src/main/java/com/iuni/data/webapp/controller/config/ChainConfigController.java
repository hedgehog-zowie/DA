package com.iuni.data.webapp.controller.config;

import com.iuni.data.common.ConfigConstants;
import com.iuni.data.persist.domain.config.Chain;
import com.iuni.data.persist.domain.config.ChainStep;
import com.iuni.data.persist.domain.config.ChainStepType;
import com.iuni.data.persist.domain.config.RTag;
import com.iuni.data.webapp.annotation.FormModel;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.response.StatusResponse;
import com.iuni.data.webapp.service.config.ChainService;
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
@RequestMapping("/config/chain")
public class ChainConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ChainConfigController.class);

    @Autowired
    private ChainService chainService;
    @Autowired
    private PageTagService pageTagService;

    /**
     * 返回关键路径页面，分页展示有效链接
     *
     * @param currentPage
     * @param pageSize
     * @param chain
     * @return
     */
    @RequestMapping
    public ModelAndView listChain(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                  @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                  @FormModel("chain") Chain chain) {
        List<Chain> chainList;
        // 1 is not setBasicInfoForCreate
        chain.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        // 0 is not cancel
        chain.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        chainList = chainService.listChain(page, chain);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain.getPath());
        modelAndView.addObject("chainList", chainList);
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 返回添加页面
     *
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addChain() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_edit.getPath());
        Chain chain = new Chain();
        modelAndView.addObject("chain", chain);
        return modelAndView;
    }

    /**
     * 返回编辑页面
     *
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editChain(@RequestParam(value = "id", required = true) Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_edit.getPath());
        Chain chain = chainService.getChainById(id);
        modelAndView.addObject("chain", chain);
        return modelAndView;
    }

    /**
     * 保存，如ID不为0，则修改，如ID为0，则添加
     *
     * @param chain
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public StatusResponse saveChain(@FormModel("chain") Chain chain) {
        logger.info("save chain: {}", chain.toString());
        if (chain.getId() == 0 && chainService.addChain(chain))
            return new StatusResponse(StatusResponse.STATUS_CODE_OK, "添加成功", StatusResponse.CALL_BACK_TYPE_CLOSE);
        else if (chain.getId() != 0 && chainService.updateChain(chain))
            return new StatusResponse(StatusResponse.STATUS_CODE_OK, "修改成功", StatusResponse.CALL_BACK_TYPE_CLOSE);
        return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "保存失败");
    }

    /**
     * 批量启用
     *
     * @param ids
     * @return
     */
    @RequestMapping("/enable")
    @ResponseBody
    public StatusResponse enableChain(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("enable page tags. ids: {}", ids);
        if (!chainService.enableOrDisableChain(ids, ConfigConstants.STATUS_FLAG_EFFECTIVE))
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "启用失败");
        return new StatusResponse(StatusResponse.STATUS_CODE_OK, "启用成功");
    }

    /**
     * 批量禁用
     *
     * @param ids
     * @return
     */
    @RequestMapping("/disable")
    @ResponseBody
    public StatusResponse disableChain(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("disable page tags. ids: {}", ids);
        if (!chainService.enableOrDisableChain(ids, ConfigConstants.STATUS_FLAG_INVALID))
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "禁用失败");
        return new StatusResponse(StatusResponse.STATUS_CODE_OK, "禁用成功");
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public StatusResponse deleteChain(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("delete page tags. ids: {}", ids);
        if (!chainService.deleteChain(ids))
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "删除失败");
        return new StatusResponse(StatusResponse.STATUS_CODE_OK, "删除成功");
    }

    /**
     * 返回关键路径步骤页面，分页展示相关关键路径的步骤
     *
     * @param currentPage
     * @param pageSize
     * @param chain
     * @return
     */
    @RequestMapping("/listStep")
    public ModelAndView listStep(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                 @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                 @FormModel("chain") Chain chain) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step.getPath());
        chain = chainService.getChainById(chain.getId());
        modelAndView.addObject("chain", chain);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        // step type 1 is page
        modelAndView.addObject("stepPageList", chainService.listChainStep(page, chain, ConfigConstants.CHAIN_STEP_TYPE_PAGE));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 返回关键路径步骤 - 页面配置
     *
     * @param chain
     * @return
     */
    @RequestMapping("/listStepPage")
    public ModelAndView listStepPage(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                     @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                     @FormModel("chain") Chain chain) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_page.getPath());
        modelAndView.addObject("chain", chain);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        // step type 1 is page
        modelAndView.addObject("stepPageList", chainService.listChainStep(page, chain, ConfigConstants.CHAIN_STEP_TYPE_PAGE));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 添加步骤 - 页面配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/addStepPage")
    public ModelAndView addStepPage(@FormModel("step") ChainStep chainStep) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_page_edit.getPath());
        ChainStepType chainStepType = new ChainStepType();
        // step type 1 is page
        chainStepType.setId(ConfigConstants.CHAIN_STEP_TYPE_PAGE);
        chainStep.setChainStepType(chainStepType);
        // get chain
        Chain chain = chainService.getChainById(chainStep.getChain().getId());
        chainStep.setChain(chain);
        return modelAndView;
    }

    /**
     * 编辑步骤 - 页面配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/editStepPage")
    public ModelAndView editStepPage(@FormModel("step") ChainStep chainStep) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_page_edit.getPath());
        chainStep = chainService.getChainStepById(chainStep.getId());
        modelAndView.addObject("step", chainStep);
        return modelAndView;
    }

    /**
     * 保存步骤 - 页面配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/saveStepPage")
    @ResponseBody
    public StatusResponse saveStepPage(@FormModel("step") ChainStep chainStep) {
        if (chainStep.getId() == 0) {
            if (chainService.addChainStep(chainStep))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "添加成功", StatusResponse.CALL_BACK_TYPE_CLOSE, "", "pageBox", "");
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "添加失败");
        } else {
            if (chainService.updateChainStep(chainStep))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "修改成功", StatusResponse.CALL_BACK_TYPE_CLOSE, "", "pageBox", "");
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "修改失败");
        }
    }

    /**
     * 逻辑删除步骤 - 页面配置
     *
     * @param ids
     * @return
     */
    @RequestMapping("/deleteStepPage")
    @ResponseBody
    public StatusResponse deleteStepPage(@RequestParam(value = "ids", required = true) String ids) {
        if (!chainService.deleteChainStep(ids))
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "删除失败");
        return new StatusResponse(StatusResponse.STATUS_CODE_OK, "删除成功", "", "", "pageBox", "");
    }

    /**
     * 返回关键路径步骤 - 功能配置
     *
     * @param currentPage
     * @param pageSize
     * @param chain
     * @return
     */
    @RequestMapping("/listStepAction")
    public ModelAndView listStepAction(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                       @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                       @FormModel("chain") Chain chain) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_action.getPath());
        modelAndView.addObject("chain", chain);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        // step type 2 is action
        modelAndView.addObject("stepActionList", chainService.listChainStep(page, chain, ConfigConstants.CHAIN_STEP_TYPE_ACTION));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 添加步骤 - 功能配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/addStepAction")
    public ModelAndView addStepAction(@FormModel("step") ChainStep chainStep) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_action_edit.getPath());
        ChainStepType chainStepType = new ChainStepType();
        // step type 2 is page
        chainStepType.setId(ConfigConstants.CHAIN_STEP_TYPE_ACTION);
        chainStep.setChainStepType(chainStepType);
        // get chain
        Chain chain = chainService.getChainById(chainStep.getChain().getId());
        chainStep.setChain(chain);
        return modelAndView;
    }

    /**
     * 编辑步骤 - 功能配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/editStepAction")
    public ModelAndView editStepAction(@FormModel("step") ChainStep chainStep) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_action_edit.getPath());
        chainStep = chainService.getChainStepById(chainStep.getId());
        modelAndView.addObject("step", chainStep);
        return modelAndView;
    }

    /**
     * 保存步骤 - 功能配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/saveStepAction")
    @ResponseBody
    public StatusResponse saveStepAction(@FormModel("step") ChainStep chainStep) {
        if (chainStep.getId() == 0) {
            if (chainService.addChainStep(chainStep))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "添加成功", StatusResponse.CALL_BACK_TYPE_CLOSE, "", "actionBox", "");
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "添加失败");
        } else {
            if (chainService.updateChainStep(chainStep))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "修改成功", StatusResponse.CALL_BACK_TYPE_CLOSE, "", "actionBox", "");
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "修改失败");
        }
    }

    /**
     * 逻辑删除步骤 - 功能配置
     *
     * @param ids
     * @return
     */
    @RequestMapping("/deleteStepAction")
    @ResponseBody
    public StatusResponse deleteStepAction(@RequestParam(value = "ids", required = true) String ids) {
        if (!chainService.deleteChainStep(ids))
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "删除失败");
        return new StatusResponse(StatusResponse.STATUS_CODE_OK, "删除成功", "", "", "actionBox", "");
    }

    /**
     * 返回关键路径步骤 - 数据统计配置
     *
     * @param currentPage
     * @param pageSize
     * @param chain
     * @return
     */
    @RequestMapping("/listStepData")
    public ModelAndView listStepData(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                     @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                     @FormModel("chain") Chain chain) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_data.getPath());
        modelAndView.addObject("chain", chain);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        // step type 3 is data
        modelAndView.addObject("stepDataList", chainService.listChainStep(page, chain, ConfigConstants.CHAIN_STEP_TYPE_DATA));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    /**
     * 添加步骤 - 数据统计配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/addStepData")
    public ModelAndView addStepData(@FormModel("step") ChainStep chainStep) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_data_edit.getPath());
        ChainStepType chainStepType = new ChainStepType();
        // step type 3 is page
        chainStepType.setId(ConfigConstants.CHAIN_STEP_TYPE_DATA);
        chainStep.setChainStepType(chainStepType);
        // get chain
        Chain chain = chainService.getChainById(chainStep.getChain().getId());
        chainStep.setChain(chain);
        return modelAndView;
    }

    /**
     * 编辑步骤 - 数据统计配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/editStepData")
    public ModelAndView editStepData(@FormModel("step") ChainStep chainStep) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_data_edit.getPath());
        chainStep = chainService.getChainStepById(chainStep.getId());
        modelAndView.addObject("step", chainStep);
        return modelAndView;
    }

    /**
     * 保存步骤 - 数据统计配置
     *
     * @param chainStep
     * @return
     */
    @RequestMapping("/saveStepData")
    @ResponseBody
    public StatusResponse saveStepData(@FormModel("step") ChainStep chainStep) {
        if (chainStep.getId() == 0) {
            if (chainService.addChainStep(chainStep))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "添加成功", StatusResponse.CALL_BACK_TYPE_CLOSE, "", "dataBox", "");
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "添加失败");
        } else {
            if (chainService.updateChainStep(chainStep))
                return new StatusResponse(StatusResponse.STATUS_CODE_OK, "修改成功", StatusResponse.CALL_BACK_TYPE_CLOSE, "", "dataBox", "");
            else
                return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "修改失败");
        }
    }

    /**
     * 删除步骤 - 数据统计配置
     *
     * @param ids
     * @return
     */
    @RequestMapping("/deleteStepData")
    @ResponseBody
    public StatusResponse deleteStepData(@RequestParam(value = "ids", required = true) String ids) {
        if (!chainService.deleteChainStep(ids))
            return new StatusResponse(StatusResponse.STATUS_CODE_ERROR, "删除失败");
        return new StatusResponse(StatusResponse.STATUS_CODE_OK, "删除成功", "", "", "dataBox", "");
    }

    /**
     * 列出功能以供选择
     *
     * @param currentPage
     * @param pageSize
     * @param rTag
     * @return
     */
    @RequestMapping("/listRTags")
    public ModelAndView listRTags(@RequestParam(value = "pageNum", required = false) Integer currentPage,
                                  @RequestParam(value = "numPerPage", required = false) Integer pageSize,
                                  @FormModel("rTag") RTag rTag) {
        List<RTag> rTagList;
        rTag.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        PageVO page = PageVO.createPage(currentPage, pageSize);
        rTagList = pageTagService.listPageTagByPage(page, rTag);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_chain_step_action_rtag.getPath());
        modelAndView.addObject("rTagList", rTagList);
        modelAndView.addObject("page", page);
//        modelAndView.addObject("rTag", rTag);
        return modelAndView;
    }

    /* ================= */
    /* getter and setter */
    /* ================= */

}
