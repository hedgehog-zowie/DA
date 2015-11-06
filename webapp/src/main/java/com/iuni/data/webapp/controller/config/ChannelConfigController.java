package com.iuni.data.webapp.controller.config;

import com.iuni.data.persist.domain.ConfigConstants;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.persist.domain.config.ChannelType;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.GenerateShortUrlUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.config.ChannelService;
import com.iuni.data.webapp.service.config.ChannelTypeService;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@Controller
@RequestMapping("/config/channel")
public class ChannelConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ChannelConfigController.class);

    @Autowired
    private ChannelService channelService;
    @Autowired
    private ChannelTypeService channelTypeService;

    /**
     * 渠道配置
     *
     * @return
     */
    @RequestMapping
    public ModelAndView listChannel() {
        List<Channel> channelList;
        Channel channel = new Channel();
        // 0 表示有效
        channel.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
//        channel.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        channelList = channelService.listChannel(channel);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_channel.getPath());
        modelAndView.addObject("channelList", channelList);
        return modelAndView;
    }

    /**
     * 添加渠道
     *
     * @return
     */
    @RequestMapping("/add")
    public ModelAndView addChannel() {
        return addOrEditChannel(new Channel());
    }

    /**
     * 编辑渠道
     *
     * @param id
     * @return
     */
    @RequestMapping("/edit")
    public ModelAndView editChannel(@RequestParam(value = "id", required = true) Long id) {
        return addOrEditChannel(channelService.getById(id));
    }

    private ModelAndView addOrEditChannel(Channel channel) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_channel_edit.getPath());
        modelAndView.addObject("channel", channel);

        ChannelType channelType = new ChannelType();
        channelType.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
        channelType.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        modelAndView.addObject("channelTypes", channelTypeService.listChannelType(channelType));

        return modelAndView;
    }

    /**
     * 保存渠道
     *
     * @return
     */
    @RequestMapping("/save")
    public String saveChannel(@ModelAttribute("channel") Channel channel) {
        logger.info("save channel: {}", channel.toString());
        ChannelType channelType =channelTypeService.getById(channel.getChannelType().getId());
        channel.setCode(String.valueOf(channelType.getCode()) + String.valueOf(channel.getChannelSerial()) + String.valueOf(channel.getActiveDate()));
        channel.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
        if (channel.getId() == 0)
            channelService.addChannel(channel);
        else
            channelService.updateChannel(channel);
        return "redirect:/config/channel";
    }

    /**
     * 逻辑删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public String deleteChannel(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("delete channel. ids: {}", ids);
        channelService.deleteChannel(ids);
        return "redirect:/config/channel";
    }

    /**
     * 启用
     *
     * @return
     */
    @RequestMapping("/enable")
    public String enableChannel(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("enable channel. ids: {}", ids);
        channelService.enableChannel(ids);
        return "redirect:/config/channel";
    }

    /**
     * 禁用
     *
     * @return
     */
    @RequestMapping("/disable")
    public String disableChannel(@RequestParam(value = "ids", required = true) String ids) {
        logger.info("disable channel. ids: {}", ids);
        channelService.disableChannel(ids);
        return "redirect:/config/channel";
    }

    /**
     * 检查是否存在此code
     *
     * @param channelId
     * @param channelTypeId
     * @param channelSerial
     * @param datepicker
     * @return
     */
    @RequestMapping("/existCode")
    @ResponseBody
    public boolean isExistChannelCode(Long channelId, Long channelTypeId, String channelSerial, String datepicker) {
        ChannelType channelType = channelTypeService.getById(channelTypeId);
        Channel channel = channelService.getByCode(channelType.getCode() + channelSerial + datepicker);
        if (channel == null)
            return false;
        if (channel.getId() == channelId)
            return false;
        return true;
    }

    /**
     * 生成短链接
     *
     * @param originalUrl
     * @param channelTypeId
     * @param channelSerial
     * @param datepicker
     * @return
     */
    @RequestMapping("/generatePromotionUrl")
    @ResponseBody
    public String generatePromotionUrl(String originalUrl, Long channelTypeId, String channelSerial, String datepicker) {
        ChannelType channelType = channelTypeService.getById(channelTypeId);
        return originalUrl + "?ad_id=" + channelType.getCode() + channelSerial + datepicker;
    }

    /**
     * 生成短链接
     *
     * @param longUrl
     * @return
     */
    @RequestMapping("/generateShortUrl")
    @ResponseBody
    public String generateShortUrl(String longUrl) {
        return GenerateShortUrlUtils.generateBaiduShortUrl(longUrl);
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
            String fileName = new String(("渠道列表").getBytes(), "ISO8859-1");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式

            List<Channel> channelList;
            Channel channel = new Channel();
            // 0 表示有效
            channel.setCancelFlag(ConfigConstants.LOGICAL_CANCEL_FLAG_NOT_CANCEL);
            channel.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
            channelList = channelService.listChannel(channel);

            List<ExcelUtils.SheetData> sheetDataList = new ArrayList<>();
            sheetDataList.add(new ExcelUtils.SheetData("渠道列表", Channel.generateTableHeader(), Channel.generateTableData(channelList)));
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
