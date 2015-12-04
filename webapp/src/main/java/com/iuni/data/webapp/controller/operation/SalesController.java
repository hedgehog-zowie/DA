package com.iuni.data.webapp.controller.operation;

import com.iuni.data.persist.model.system.OrderSourceDto;
import com.iuni.data.persist.model.operation.SalesQueryDto;
import com.iuni.data.persist.model.operation.SalesTableDto;
import com.iuni.data.persist.model.wares.CategoryDto;
import com.iuni.data.persist.model.wares.SkuDto;
import com.iuni.data.persist.model.wares.WareDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.DateStyle;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.operation.SalesService;
import com.iuni.data.webapp.service.system.SystemConstantsService;
import com.iuni.data.webapp.service.wares.WareService;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller("salesOfOperation")
@RequestMapping("/operation/sales")
public class SalesController {

    private static final Logger logger = LoggerFactory.getLogger(SalesController.class);

    @Autowired
    private SalesService salesService;
    @Autowired
    private WareService wareService;
    @Autowired
    private SystemConstantsService systemConstantsService;

    @RequestMapping
    public ModelAndView queryTable() {
        SalesQueryDto queryParam = new SalesQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        queryParam.setDateStyle(DateStyle.YYYYMMDD.getDateStyleStr());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") SalesQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.operation_sales.getPath());
        // 解析参数
        queryParam.parseDateRangeString();
        queryParam.parseOrderSource();
        queryParam.parseSku();
        // 查询结果
        List<SalesTableDto> resultList = salesService.selectSales(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("dateStyles", genDateStyle());
        List<OrderSourceDto> orderSources = systemConstantsService.selectAllOMOrderSource();
        modelAndView.addObject("orderSources", orderSources);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        SalesQueryDto queryParam = JsonUtils.fromJson(queryParamStr, SalesQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("商品销售报表(" + queryParam.getDateRangeString() + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + ".xlsx\"");

            // 解析参数
            queryParam.parseDateRangeString();
            queryParam.parseOrderSource();
            queryParam.parseSku();
            List<SalesTableDto> resultList = salesService.selectSales(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("商品销售", SalesTableDto.generateTableHeader(), SalesTableDto.generateTableData(resultList)));
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

    @ResponseBody
    @RequestMapping("selectCategory")
    public List<CategoryDto> selectCategory(){
        return wareService.selectCategoryExceptPhone();
    }

    @ResponseBody
    @RequestMapping("selectWareOfPhone")
    public List<WareDto> selectWareOfPhone(){
        String catIdOfPhone = "1083694";
        return wareService.selectWareByCategory(catIdOfPhone);
    }

    @ResponseBody
    @RequestMapping("selectWare")
    public List<WareDto> selectWare(String catId){
        return wareService.selectWareByCategory(catId);
    }

    @ResponseBody
    @RequestMapping("selectSku")
    public List<SkuDto> selectSku(String wareId){
        return wareService.selectSkuByWare(wareId);
    }

    private List<DateStyle> genDateStyle() {
        List<DateStyle> list = new ArrayList<DateStyle>();
        list.add(DateStyle.YYYYMMDD);
        list.add(DateStyle.YYYYMM);
        list.add(DateStyle.YYYYIW);
        return list;
    }

}
