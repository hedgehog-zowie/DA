package com.iuni.data.webapp.controller.config;

import com.iuni.data.persist.domain.config.Advertisement;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.service.config.AdvertisementConfigService;
import com.iuni.data.webapp.sso.dto.ShiroUser;
import com.iuni.data.webapp.sso.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/config/advertisement")
public class AdvertisementController {

    @Autowired
    private AdvertisementConfigService advertisementConfigService;

    @Autowired
    private AccountService accountService;

    ModelAndView modelAndView = new ModelAndView();

    @RequestMapping
    public ModelAndView advertise(Map<String, Object> map, Integer pageNum, Integer numPerPage, Advertisement advertisement) {
        PageVO page = new PageVO();
        if (pageNum != null) {
            //设置页码
            page.setCurrentPage(pageNum);
        }
//      设置每页显示条数
        if (numPerPage != null) {
            page.setPageSize(numPerPage);
        } else {
            page.setPageSize(20);
        }

        List<Advertisement> advertisements = advertisementConfigService.findAllQueryDsl(advertisement, page);

        map.put("advertisements", advertisements);
        map.put("page", page);
        map.put("advertisement", advertisement);
        modelAndView.setViewName("config/config-advertisement");
        return modelAndView;
    }


    //跳转到添加记录的页面
    @RequestMapping(value = "/add")
    public ModelAndView addAdvertisement() {
        modelAndView.setViewName("config/config-advertisement-add");
        return modelAndView;
    }

    //添加一条记录
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    String addAdvertisement(Advertisement advertisement) {

        //保存创建日期和更新日期
        Date date = new Date();
        advertisement.setCreateDate(date);
        advertisement.setUpdateDate(date);
        //获取操作者并保存
        ShiroUser user = accountService.getCurrentUser();
        advertisement.setCreateBy(user.getLoginName());
        advertisement.setUpdateBy(user.getLoginName());
        //将status设为1
        advertisement.setStatus(1);
        //将cancelFlag设为1
        advertisement.setCancelFlag(0);
        String str = null;
        try {
            //保存一条advertisement记录
            advertisementConfigService.save(advertisement);
            //返回json字符串
            str = "{\"statusCode\":\"200\","
                    + "\"message\":\"操作成功\","
                    + "\"navTabId\":\"config/advertisement\","
                    + "\"rel\":\"\","
                    + "\"callbackType\":\"closeCurrent\","
                    + "\"forwardUrl\":\"\"} ";
            return str;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            str = "{\"statusCode\":\"300\","
                    + "\"message\":\"操作失败\"} ";
            return str;
        }

    }

    //跳转到update页面
    @RequestMapping(value = "/update")
    public ModelAndView update(@RequestParam("id") Long id, Map<String, Object> map) {
        Advertisement advertisement = advertisementConfigService.loadById(id);

        map.put("advertisement", advertisement);
        modelAndView.setViewName("config/config-advertisement-setBasicInfoForUpdate");
        return modelAndView;
    }

    //更新Advertisement记录
    @RequestMapping(value = "/updateAdvertisement")
    @ResponseBody
    public String updateAdvertisement(Advertisement advertisement, Map<String, Object> map) {
        //获取操作者并保存
        ShiroUser user = accountService.getCurrentUser();
        advertisement.setUpdateBy(user.getLoginName());
        //保存创建日期和修改更新日期
        advertisement.setCreateDate(advertisementConfigService.loadById(advertisement.getId()).getCreateDate());
        Date date = new Date();
        advertisement.setUpdateDate(date);
        advertisementConfigService.update(advertisement);

        String str = "{\"statusCode\":\"200\","
                + "\"message\":\"操作成功\","
                + "\"navTabId\":\"config/advertisement\","
                + "\"rel\":\"\","
                + "\"callbackType\":\"closeCurrent\","
                + "\"forwardUrl\":\"\"} ";
        return str;

    }

    //通过Id来更新记录，将status置为0或1
    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public String updateStatusById(Long id) {
        Advertisement advertisement = advertisementConfigService.loadById(id);
        if (advertisement != null) {
            if (advertisement.getStatus() == 1) {
                advertisement.setStatus(0);
            } else if (advertisement.getStatus() == 0) {
                advertisement.setStatus(1);
            }
        }

        advertisementConfigService.update(advertisement);
        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/advertisement\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/advertisement\","
                + "\"confirmMsg\":\"\"}";

    }

    //批量更新status 全部将status置为0
    @RequestMapping(value = "/updateBatchZero")
    @ResponseBody
    public String updateBatchZero(String ids) {
        String[] idss = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < idss.length; i++) {
            list.add(Long.parseLong(idss[i]));
        }
        Iterable<Long> its = list;
        List<Advertisement> advertisements = advertisementConfigService.findAllByIds(its);
        for (Advertisement i : advertisements) {
            i.setStatus(0);
        }
        Iterable<Advertisement> itss = advertisements;
        advertisementConfigService.saveAll(itss);
        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/advertisement\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/advertisement\","
                + "\"confirmMsg\":\"\"}";
    }

    //批量更新status 全部将status置为1
    @RequestMapping(value = "/updateBatchOne")
    @ResponseBody
    public String updateBatchOne(String ids) {
        String[] idss = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < idss.length; i++) {
            list.add(Long.parseLong(idss[i]));
        }
        Iterable<Long> its = list;
        List<Advertisement> advertisements = advertisementConfigService.findAllByIds(its);
        for (Advertisement i : advertisements) {
            i.setStatus(1);
        }
        Iterable<Advertisement> itss = advertisements;
        advertisementConfigService.saveAll(itss);
        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/advertisement\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/advertisement\","
                + "\"confirmMsg\":\"\"}";
    }

    //删除
    @RequestMapping(value = "/updateBatch")
    @ResponseBody
    public String updateBatch(String ids, Integer numPerPage) {

        String[] idss = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < idss.length; i++) {
            list.add(Long.parseLong(idss[i]));
        }
        Iterable<Long> its = list;
        List<Advertisement> advertisements = advertisementConfigService.findAllByIds(its);
        for (Advertisement i : advertisements) {
            i.setCancelFlag(1);
        }
        Iterable<Advertisement> itss = advertisements;
        advertisementConfigService.saveAll(itss);
        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/advertisement\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/advertisement?numPerPage=" + numPerPage + "\","
                + "\"confirmMsg\":\"删除成功\"}";
    }

    //删除一条记录，将cancelflag置为1
    @RequestMapping(value = "/updateAdvertisementCancelFlag")
    @ResponseBody
    public String updateAdvertisementCancelFlag(Long id, Map<String, Object> map) {
        Advertisement advertisement = advertisementConfigService.loadById(id);
        if (advertisement != null) {
            advertisement.setCancelFlag(1);
        }

        advertisementConfigService.update(advertisement);
        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/advertisement\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/advertisement\","
                + "\"confirmMsg\":\"\"}";

    }
}
