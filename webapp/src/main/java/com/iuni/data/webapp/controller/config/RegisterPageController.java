package com.iuni.data.webapp.controller.config;

import com.iuni.data.persist.domain.config.RegisterPage;
import com.iuni.data.webapp.common.PageVO;
import com.iuni.data.webapp.service.config.RegisterPageConfigService;
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
@RequestMapping("config/registerPage")
public class RegisterPageController {

    @Autowired
    private RegisterPageConfigService registerPageConfigService;
    @Autowired
    private AccountService accountService;

    ModelAndView modelAndView = new ModelAndView();

    //分页查询
    @RequestMapping
    public ModelAndView register(Map<String, Object> map, Integer pageNum, Integer numPerPage, RegisterPage registerPage) {

        PageVO page = new PageVO();
        if (pageNum != null) {
            //设置页码
            page.setCurrentPage(pageNum);
        }
//        设置每页显示条数
        if (numPerPage != null) {
            page.setPageSize(numPerPage);
        } else {
            page.setPageSize(20);
        }
//        List<IuniDaRegisterPage> registerPages = registerPageConfigService.findPagination(page);
        List<RegisterPage> registerPages = registerPageConfigService.findAllQueryDsl(registerPage, page);
        map.put("registerPages", registerPages);
        map.put("page", page);
        map.put("registerPage", registerPage);
        modelAndView.setViewName("config/config-registerPage");
        return modelAndView;
    }

    //跳转至添加页面
    @RequestMapping(value = "/addregisterPage")
    public ModelAndView addregisterPage() {

        modelAndView.setViewName("config/config-registerPage-add");
        return modelAndView;
    }

    //添加一条registerPage记录
    @RequestMapping(value = "/addregisterPage", method = RequestMethod.POST)
    public
    @ResponseBody
    String addregisterPage(RegisterPage registerPage) {
        //保存创建日期，日期格式在DateUtil.getFormatDate()中
        Date date = new Date();
        registerPage.setCreateDate(date);
        registerPage.setUpdateDate(date);
        //获取操作者并保存
        ShiroUser user = accountService.getCurrentUser();
        registerPage.setCreateBy(user.getLoginName());
        registerPage.setUpdateBy(user.getLoginName());
        //将status设为
        registerPage.setStatus(1);
        //将cancelFlag设为0,0:未删除,1:已删除
        registerPage.setCancelFlag(0);

        //保存一条registerPage记录
        registerPageConfigService.save(registerPage);
        //返回json字符串
        String str = "{\"statusCode\":\"200\","
                + "\"message\":\"操作成功\","
                + "\"navTabId\":\"config/registerPage\","
                + "\"rel\":\"\","
                + "\"callbackType\":\"closeCurrent\","
                + "\"forwardUrl\":\"\"} ";
        return str;
//        StatusResponse sr = new StatusResponse(StatusResponse.STATUS_CODE_OK,"Ok");
//        return sr;

    }

    //跳转到update页面
    @RequestMapping(value = "/update")
    public ModelAndView update(@RequestParam("id") Long id, Map<String, Object> map) {
        RegisterPage registerPage = registerPageConfigService.loadById(id);
        map.put("registerPage", registerPage);
        modelAndView.setViewName("config/config-registerPage-setBasicInfoForUpdate");
        return modelAndView;
    }

    //更新IuniDaRegisterPage记录
    @RequestMapping(value = "/updateIuniDaRegisterPage", method = RequestMethod.POST)
    @ResponseBody
    public String updateIuniDaRegisterPage(RegisterPage registerPage, Map<String, Object> map) {
        //获取操作者并保存
        ShiroUser user = accountService.getCurrentUser();
        registerPage.setUpdateBy(user.getLoginName());
        //保存创建日期，日期格式在DateUtil.getFormatDate()中

        registerPage.setCreateDate(registerPageConfigService.loadById(registerPage.getId()).getCreateDate());
        registerPage.setUpdateDate(new Date());
        registerPageConfigService.update(registerPage);
//        StatusResponse sr = new StatusResponse(StatusResponse.STATUS_CODE_OK,"Ok");
//        return sr;
        String str = "{\"statusCode\":\"200\","
                + "\"message\":\"操作成功\","
                + "\"navTabId\":\"config/registerPage\","
                + "\"rel\":\"\","
                + "\"callbackType\":\"closeCurrent\","
                + "\"forwardUrl\":\"\"} ";
        return str;

    }

    //通过Id来更新记录，将status置为0或1
    @RequestMapping(value = "/updateStatus")
    @ResponseBody
    public String updateStatusById(Long id) {
        RegisterPage registerPage = registerPageConfigService.loadById(id);
        if (registerPage != null) {
            if (registerPage.getStatus() == 1) {
                registerPage.setStatus(0);
            } else if (registerPage.getStatus() == 0) {
                registerPage.setStatus(1);
            }
        }

        registerPageConfigService.update(registerPage);

        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/registerPage\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/registerPage\","
                + "\"confirmMsg\":\"\"}";

    }

    //批量更新status 全部将status置为0
    @RequestMapping(value = "/updateBatchZero")
    @ResponseBody
    public String updateBatchzero(String ids) {
        String[] idss = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < idss.length; i++) {
            list.add(Long.parseLong(idss[i]));
        }
        Iterable<Long> its = list;
        List<RegisterPage> registerPages = registerPageConfigService.findAllByIds(its);
        for (RegisterPage i : registerPages) {
            i.setStatus(0);
        }
        Iterable<RegisterPage> itss = registerPages;
        registerPageConfigService.saveAll(itss);
        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/registerPage\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/registerPage\","
                + "\"confirmMsg\":\"\"}";
    }

    //批量更新status 全部将status置为1
    @RequestMapping(value = "/updateBatchOne")
    @ResponseBody
    public String updateBatch1(String ids) {
        String[] idss = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < idss.length; i++) {
            list.add(Long.parseLong(idss[i]));
        }
        Iterable<Long> its = list;
        List<RegisterPage> registerPages = registerPageConfigService.findAllByIds(its);
        for (RegisterPage i : registerPages) {
            i.setStatus(1);
        }
        Iterable<RegisterPage> itss = registerPages;
        registerPageConfigService.saveAll(itss);
        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/registerPage\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/registerPage\","
                + "\"confirmMsg\":\"\"}";
    }

    //批量将registerPage中的CancelFlag置为1
    @RequestMapping("/updateBatch")
    @ResponseBody
    public String updateBatch(String ids, Integer numPerPage) {

        String[] idss = ids.split(",");
        List<Long> list = new ArrayList<Long>();
        for (int i = 0; i < idss.length; i++) {
            list.add(Long.parseLong(idss[i]));
        }
        Iterable<Long> its = list;
        List<RegisterPage> registerPages = registerPageConfigService.findAllByIds(its);
        for (RegisterPage i : registerPages) {
            i.setCancelFlag(1);
        }
        Iterable<RegisterPage> itss = registerPages;
        registerPageConfigService.saveAll(itss);
        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/registerPage\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/registerPage?numPerPage=" + numPerPage + "\","
                + "\"confirmMsg\":\"删除成功\"}";
    }

    //删除一条记录，将cancelflag置为1
    @RequestMapping(value = "/updateIuniDaRegisterPageCancelFlag")
    @ResponseBody
    public String updateIuniDaRegisterPageCancelFlag(Long id) {
        RegisterPage registerPage = registerPageConfigService.loadById(id);
        if (registerPage != null) {
            registerPage.setCancelFlag(1);
        }

        registerPageConfigService.update(registerPage);

        return "{\"statusCode\":\"200\","
                + "\"navTabId\":\"\","
                + "\"rel\":\"config/registerPage\","
                + "\"callbackType\":\"forward\","
                + "\"forwardUrl\":\"http://localhost:8080/iuni-data-webapp/config/registerPage\","
                + "\"confirmMsg\":\"\"}";

    }
}
