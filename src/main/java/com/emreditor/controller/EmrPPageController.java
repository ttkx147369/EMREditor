package com.emreditor.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 电子病历设计页面
 */
@Controller
@RequestMapping("/ppage")
public class EmrPPageController {
    /**
     * 跳转到新增页面
     * @return
     */
    @RequestMapping("addpAddData")
    public String addpAddData() {
        return "page/addpAddData";
    }

    /**
     * 跳转到病历设计页面
     * @return
     */
    @RequestMapping("todesign")
    public String todesign(){
        return "page/design";
    }
}