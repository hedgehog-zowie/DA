package com.iuni.data.webapp.controller.config;

import com.iuni.data.persist.domain.ConfigConstants;
import com.iuni.data.persist.domain.config.BuriedPoint;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.config.BuriedPointService;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/config/buriedPoint")
public class BuriedPointConfigController {

    private static final Logger logger = LoggerFactory.getLogger(BuriedPointConfigController.class);

    @Autowired
    private BuriedPointService buriedPointService;

    /**
     * 埋点配置
     *
     * @return
     */
    @RequestMapping
    public ModelAndView listBuriedPoint() {
        List<BuriedPoint> buriedPointList;
        BuriedPoint buriedPoint = new BuriedPoint();
        // 0 表示有效
        buriedPoint.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
//        buriedPoint.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        buriedPointList = buriedPointService.listBuriedPoint(buriedPoint);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_buriedPoint.getPath());
        modelAndView.addObject("buriedPointList", buriedPointList);
        return modelAndView;
    }

    /**
     * 添加埋点
     *
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addBuriedPoint() {
        return addOrEditBuriedPoint(new BuriedPoint());
    }

    /**
     * 编辑埋点
     *
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editBuriedPoint(@RequestParam(value = "id", required = true) Long id) {
        return addOrEditBuriedPoint(buriedPointService.getById(id));
    }

    private ModelAndView addOrEditBuriedPoint(BuriedPoint buriedPoint){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_buriedPoint_edit.getPath());

        modelAndView.addObject("buriedPoint", buriedPoint);
        return modelAndView;
    }

    /**
     * 保存渠道
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveBuriedPoint(@ModelAttribute("buriedPoint") BuriedPoint buriedPoint) {
        logger.info("save buriedPoint: {}", buriedPoint.toString());
        buriedPoint.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        if (buriedPoint.getId() == 0)
            buriedPointService.addBuriedPoint(buriedPoint);
        else
            buriedPointService.updateBuriedPoint(buriedPoint);
        return "redirect:/config/buriedPoint";
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public String deleteBuriedPoint(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("delete buriedPoint. ids: {}", ids);
        buriedPointService.deleteBuriedPoint(ids);
        return "redirect:/config/buriedPoint";
    }

    /**
     * 启用
     * @return
     */
    @RequestMapping("/enable")
    public String enableBuriedPoint(@RequestParam(value = "ids", required = true) String ids){
        logger.info("enable buriedPoint. ids: {}", ids);
        buriedPointService.enableBuriedPoint(ids);
        return "redirect:/config/buriedPoint";
    }

    /**
     * 禁用
     * @return
     */
    @RequestMapping("/disable")
    public String disableBuriedPoint(@RequestParam(value = "ids", required = true) String ids){
        logger.info("disable buriedPoint. ids: {}", ids);
        buriedPointService.disableBuriedPoint(ids);
        return "redirect:/config/buriedPoint";
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
            String fileName = new String(("埋点列表").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式

            List<BuriedPoint> buriedPointList;
            BuriedPoint buriedPoint = new BuriedPoint();
            // 0 表示有效
            buriedPoint.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
            buriedPoint.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
            buriedPointList = buriedPointService.listBuriedPoint(buriedPoint);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("埋点列表", BuriedPoint.generateTableHeader(), BuriedPoint.generateTableData(buriedPointList)));
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
