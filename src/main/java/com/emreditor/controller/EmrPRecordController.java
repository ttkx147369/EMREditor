package com.emreditor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 电子病历填写界面
 */
@Controller
@RequestMapping("/record")
public class EmrPRecordController {
    @RequestMapping("record")
    public String addpAddData() {
        return "record/record";
    }
}
