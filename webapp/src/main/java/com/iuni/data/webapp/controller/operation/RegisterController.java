package com.iuni.data.webapp.controller.operation;

import com.iuni.data.persist.model.operation.RegisterQueryDto;
import com.iuni.data.persist.model.operation.RegisterTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.operation.UserService;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 会员注册统计报表
 *
 * @author zowie
 *         Email:   nicholas.chen@iuni.com
 */
@Controller("registerOfOperation")
@RequestMapping("/operation/register")
public class RegisterController {

    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;

    /**
     * 表格数据,默认参数
     *
     * @return
     */
    @RequestMapping
    public ModelAndView queryTable() {
        RegisterQueryDto queryParam = new RegisterQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    /**
     * 表格数据,传入参数
     *
     * @param queryParam
     * @return
     */
    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") RegisterQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        // 页面路径
        modelAndView.setViewName(PageName.operation_register.getPath());
        // 解析参数
        queryParam.parseDateRangeString();
        // 查询结果
        List<RegisterTableDto> resultList = userService.selectRegister(queryParam);
        modelAndView.addObject("resultList", resultList);
        // 返回结果
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        // 获取参数
        RegisterQueryDto queryParam = JsonUtils.fromJson(queryParamStr, RegisterQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            // 导出excel文件名
            String fileName = new String(("会员注册统计报表(" + queryParam.getDateRangeString() + ")").getBytes(), "ISO8859-1");
            // 文件名可包含空格
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");
            // 解析参数
            queryParam.parseDateRangeString();
            // 查询结果
            List<RegisterTableDto> resultList = userService.selectRegister(queryParam);
            // 导出结果
            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("会员注册", RegisterTableDto.generateTableHeader(), RegisterTableDto.generateTableData(resultList)));
            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(sheetDataList);
            wb.write(response.getOutputStream());

            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            logger.error("export to excel error. {}", e.getMessage(), e);
        } finally {
            try {
                response.getOutputStream().close();
            } catch (IOException e) {
                logger.error("export to excel error. {}", e.getMessage(), e);
            }
        }
        return null;
    }

}
