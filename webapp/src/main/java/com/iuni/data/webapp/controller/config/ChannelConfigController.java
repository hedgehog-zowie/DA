package com.iuni.data.webapp.controller.config;

import com.iuni.data.common.ConfigConstants;
import com.iuni.data.persist.domain.config.Channel;
import com.iuni.data.utils.ExcelUtils;
import com.iuni.data.utils.GenerateShortUrlUtils;
import com.iuni.data.webapp.common.PageName;
import com.iuni.data.webapp.service.config.ChannelService;
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
import java.util.*;

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
        channel.setStatus(ConfigConstants.STATUS_FLAG_EFFECTIVE);
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

    private ModelAndView addOrEditChannel(Channel channel){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.config_channel_edit.getPath());
        modelAndView.addObject("channel", channel);
        modelAndView.addObject("channelTypes", Channel.ChannelType.getAllChannelType());
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
        channel.setCode(String.valueOf(channel.getChannelType()) + String.valueOf(channel.getChannelSerial()) + String.valueOf(channel.getActiveDate()));
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
     * 检查是否存在此code
     *
     * @param channelCode
     * @return
     */
    @RequestMapping("/existCode")
    @ResponseBody
    public boolean isExistChannelCode(Integer channelId, String channelCode) {
        Channel channel = channelService.getByCode(channelCode);
        if (channel == null)
            return false;
        if (channel.getId() == channelId)
            return false;
        return true;
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

            SXSSFWorkbook wb = ExcelUtils.generateExcelWorkBook(generateTableHeaders(), generateTableDatas(channelList));
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

    /**
     * 表头
     * @return
     */
    private Map<String, String> generateTableHeaders() {
        Map<String, String> tableHeader = new LinkedHashMap<>();
        tableHeader.put("名称", "name");
        tableHeader.put("渠道AD编码", "code");
        tableHeader.put("渠道类型", "channelType");
        tableHeader.put("原始链接", "originalUrl");
        tableHeader.put("推广链接", "promotionUrl");
        tableHeader.put("短链接", "shortUrl");
        tableHeader.put("备注", "desc");
        return tableHeader;
    }

    /**
     * 表数据
     * @param channelList
     * @return
     */
    private List<Map<String, String>> generateTableDatas(List<Channel> channelList) {
        List<Map<String, String>> tableDatas = new ArrayList<>();
        for (Channel channel : channelList) {
            Map<String, String> rowData = new HashMap<>();
            rowData.put("name", channel.getName());
            rowData.put("code", channel.getCode());
            rowData.put("channelType", channel.getChannelType());
            rowData.put("originalUrl", channel.getOriginalUrl());
            rowData.put("promotionUrl", channel.getPromotionUrl());
            rowData.put("shortUrl", channel.getShortUrl());
            rowData.put("desc", channel.getDesc());
            tableDatas.add(rowData);
        }
        return tableDatas;
    }

    /* ================= */
    /* getter and setter */
    /* ================= */

}
