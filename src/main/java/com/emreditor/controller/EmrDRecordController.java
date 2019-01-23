package com.emreditor.controller;

import com.emreditor.beans.Record;
import com.emreditor.beans.RecordValue;
import com.emreditor.service.EmrRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
@RequestMapping("record")
public class EmrDRecordController {
    @Autowired
    private EmrRecordService emrRecordService;
    @RequestMapping("insertRecord")
    public int insertRecord(HttpServletRequest request, Record record, RecordValue recordValue){
        return emrRecordService.insertRecord(request, record, recordValue);
    }
}
