package com.iuni.data.webapp.controller.config;

import com.iuni.data.persist.domain.ConfigConstants;
import com.iuni.data.persist.domain.config.BuriedGroup;
import com.iuni.data.persist.domain.config.BuriedPoint;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.common.ResultOfAjax;
import com.iuni.data.webapp.service.config.BuriedGroupService;
import com.iuni.data.webapp.service.config.BuriedPointService;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/config/buriedGroup")
public class BuriedGroupConfigController {

    private static final Logger logger = LoggerFactory.getLogger(BuriedGroupConfigController.class);

    @Autowired
    private BuriedPointService buriedPointService;
    @Autowired
    private BuriedGroupService buriedGroupService;

    /**
     * 埋点组配置
     *
     * @return
     */
    @RequestMapping
    public ModelAndView listBuriedGroup() {
        List<BuriedGroup> buriedGroupList;
        BuriedGroup buriedGroup = new BuriedGroup();

        buriedGroup.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);

        buriedGroupList = buriedGroupService.listBuriedGroup(buriedGroup);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_buriedGroup.getPath());
        modelAndView.addObject("buriedGroupList", buriedGroupList);
        return modelAndView;
    }

    /**
     * 添加埋点组
     *
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addBuriedGroup() {
        return addOrEditBuriedGroup(new BuriedGroup());
    }

    /**
     * 编辑埋点组
     *
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editBuriedGroup(@RequestParam(value = "id", required = true) Long id) {
        return addOrEditBuriedGroup(buriedGroupService.getById(id));
    }

    private ModelAndView addOrEditBuriedGroup(BuriedGroup buriedGroup) {
        ModelAndView modelAndView = new ModelAndView();

        BuriedPoint buriedPoint = new BuriedPoint();
        buriedPoint.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        List<BuriedPoint> buriedPointList = buriedPointService.listBuriedPoint(buriedPoint);
        // 处理显示顺序，已经选中的按顺序排列并显示在最前面
        List<BuriedPoint> showBuriedPointList = new ArrayList<>();
        if(buriedGroup.getBuriedPoints() != null) {
            List<BuriedPoint> selectedPointList = new ArrayList<>();
            for (Long id : buriedGroup.getBuriedPoints()) {
                for (BuriedPoint bp : buriedPointList) {
                    if (bp.getId() == id) {
                        selectedPointList.add(bp);
                        break;
                    }
                }
            }
            buriedPointList.removeAll(selectedPointList);
            showBuriedPointList.addAll(selectedPointList);
            showBuriedPointList.addAll(buriedPointList);
        }else
            showBuriedPointList.addAll(buriedPointList);
        modelAndView.addObject("buriedPointList", showBuriedPointList);
        modelAndView.addObject("buriedGroup", buriedGroup);

        modelAndView.setViewName(PageName.config_buriedGroup_edit.getPath());
        return modelAndView;
    }

    /**
     * 保存
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultOfAjax saveBuriedGroup(@ModelAttribute("buriedGroup") BuriedGroup buriedGroup) {
        logger.info("save buriedGroup: {}", buriedGroup.toString());
        buriedGroup.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        ResultOfAjax result = new ResultOfAjax();
        try {
            if (buriedGroup.getId() == 0)
                buriedGroupService.addBuriedGroup(buriedGroup);
            else
                buriedGroupService.updateBuriedGroup(buriedGroup);
            result.setCode(ResultOfAjax.CODE_SUCCEED);
            result.setMsg("成功");
        } catch (Exception e) {
            result.setCode(ResultOfAjax.CODE_FAILED);
            result.setMsg(e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResultOfAjax deleteBuriedGroup(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("delete buriedGroup. ids: {}", ids);
        ResultOfAjax result = new ResultOfAjax();
        try {
            buriedGroupService.deleteBuriedGroup(ids);
            result.setCode(ResultOfAjax.CODE_SUCCEED);
            result.setMsg("成功");
        } catch (Exception e) {
            result.setCode(ResultOfAjax.CODE_FAILED);
            result.setMsg(e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 启用
     *
     * @return
     */
    @RequestMapping("/enable")
    @ResponseBody
    public ResultOfAjax enableBuriedGroup(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("enable buriedGroup. ids: {}", ids);
        ResultOfAjax result = new ResultOfAjax();
        try {
            buriedGroupService.enableBuriedGroup(ids);
            result.setCode(ResultOfAjax.CODE_SUCCEED);
            result.setMsg("成功");
        } catch (Exception e) {
            result.setCode(ResultOfAjax.CODE_FAILED);
            result.setMsg(e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 禁用
     *
     * @return
     */
    @RequestMapping("/disable")
    @ResponseBody
    public ResultOfAjax disableBuriedGroup(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("disable buriedGroup. ids: {}", ids);
        ResultOfAjax result = new ResultOfAjax();
        try {
            buriedGroupService.disableBuriedGroup(ids);
            result.setCode(ResultOfAjax.CODE_SUCCEED);
            result.setMsg("成功");
        } catch (Exception e) {
            result.setCode(ResultOfAjax.CODE_FAILED);
            result.setMsg(e.getLocalizedMessage());
        }
        return result;
    }

    /**
     * 导出
     *
     * @param response
     * @return
     */
    @RequestMapping("/exportExcel")
    public String exportExcel(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("埋点组組列表").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");// 组装附件名称和格式

            List<BuriedGroup> buriedGroupList;
            BuriedGroup buriedGroup = new BuriedGroup();

            buriedGroup.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
            buriedGroup.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
            buriedGroupList = buriedGroupService.listBuriedGroup(buriedGroup);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("埋点组組列表", BuriedGroup.generateTableHeader(), BuriedGroup.generateTableData(buriedGroupList)));
            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(sheetDataList);
            wb.write(response.getOutputStream());

            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* ================= */
    /* getter and setter */
    /* ================= */

}
