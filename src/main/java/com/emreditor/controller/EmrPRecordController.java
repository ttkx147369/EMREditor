package com.emreditor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 电子病历填写界面
 */
@Controller
@RequestMapping("/record")
public class EmrPRecordController {
    /**
     * 跳转到记录详情页
     * @return 2
     */
    @RequestMapping("record")
    public String addpAddData() {
        return "record/record";
    }

    /**
     * 跳转到记录列表页
     * @return 2
     */
    @RequestMapping("index")
    public String index(){
        return "record/index";
    }
}
