package com.iuni.data.webapp.controller;

import com.iuni.data.webapp.common.PageName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Nicholas
 *         Email:   nicholas.chen@iuni.com
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping
    public ModelAndView getHomePage() {
        return getIndexPage();
    }

    @RequestMapping("index")
    public ModelAndView getIndexPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PageName.index.getPath());
        return modelAndView;
    }

}
