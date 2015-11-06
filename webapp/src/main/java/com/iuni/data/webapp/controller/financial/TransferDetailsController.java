package com.iuni.data.webapp.controller.financial;

import com.iuni.data.persist.model.financial.StockMoveDetailsTableDto;
import com.iuni.data.persist.model.financial.TransferDetailsQueryDto;
import com.iuni.data.persist.model.financial.TransferDetailsTableDto;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.JsonUtils;
import com.iuni.data.utils.StringUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.financial.TransferService;
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
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller("transferDetailsControllerOfFinancial")
@RequestMapping("/financial/transferDetails")
public class TransferDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(TransferDetailsController.class);

    @Autowired
    private TransferService transferService;

    @RequestMapping
    public ModelAndView queryTable() {
        TransferDetailsQueryDto queryParam= new TransferDetailsQueryDto();
        queryParam.setDateRangeString(StringUtils.getLastSevenDaysRangeString());
        return queryTable(queryParam);
    }

    @RequestMapping("query")
    public ModelAndView queryTable(@ModelAttribute("queryParam") TransferDetailsQueryDto queryParam) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.financial_transfer_details.getPath());
        queryParam.parseDateRangeString();
        List<TransferDetailsTableDto> resultList = transferService.selectTransferDetails(queryParam);
        modelAndView.addObject("resultList", resultList);
        modelAndView.addObject("queryParam", queryParam);
        return modelAndView;
    }

    @RequestMapping("exportExcel")
    public String exportExcel(String queryParamStr, HttpServletResponse response) {
        TransferDetailsQueryDto queryParam = JsonUtils.fromJson(queryParamStr, TransferDetailsQueryDto.class);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            String fileName = new String(("调拔明细报表(" + queryParam.getDateRangeString().replaceAll("\\s+", "") + ")").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");

            queryParam.parseDateRangeString();
            List<TransferDetailsTableDto> resultList = transferService.selectTransferDetails(queryParam);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("调拔明细", TransferDetailsTableDto.generateTableHeader(), TransferDetailsTableDto.generateTableData(resultList)));
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
