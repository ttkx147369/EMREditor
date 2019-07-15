package com.emreditor.controller;

import com.emreditor.beans.Record;
import com.emreditor.beans.RecordResult;
import com.emreditor.service.EmrRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("drecord")
public class EmrDRecordController {
    private EmrRecordService emrRecordService;

    @Autowired
    public EmrDRecordController(EmrRecordService emrRecordService) {
        this.emrRecordService = emrRecordService;
    }

    /**
     * 条件查询记录主表信息
     *
     * @param record 1
     * @return 1
     */
    @RequestMapping("index")
    public List<Record> index(Record record) {
        return emrRecordService.drecord(record);
    }

    /**
     * 新增记录主表数据
     *
     * @param record 1
     * @return 1
     */
    @RequestMapping("insertRecord")
    public String insertRecord(Record record) {
        return emrRecordService.insertRecord(record);
    }

    /**
     * 保存评估详情
     *
     * @param request 1
     * @return 1
     */
    @RequestMapping("insertRecordValue")
    public int insertRecordValue(HttpServletRequest request) {
        return emrRecordService.insertRecordValue(request);
    }

    /**
     * 条件查询详情记录表
     *
     * @param request 1
     * @return 1
     */
    @RequestMapping("getRecordValue")
    public List<Map<String, Object>> getRecordValue(HttpServletRequest request) {
        return emrRecordService.getRecordValue(request);
    }

    /**
     * 删除评估记录信息
     *
     * @param record 1
     * @return 1
     */
    @RequestMapping("delrecord")
    public int deleterecord(Record record) {
        return emrRecordService.deleterecord(record);
    }


    //////////////////////////////////////////
    /////////////以下没有适用///////////////////
    //////////////////////////////////////////
    /**
     * 获取记录表中某一次记录的其中一些参数
     *
     * @param request names:需要获取值的多个name属性值，英文逗号分隔；idrecord：记录主表主键
     * @return 1
     */
    /*@RequestMapping("getPartRecordValue")
    public List<Map<String, Object>> getPartRecordValue(HttpServletRequest request) {
        return emrRecordService.getPartRecordValue(request);
    }*/

    @RequestMapping("saveRecordValue")
    public int saveRecordValue(RecordResult recordResult){
        return emrRecordService.saveRecordValue(recordResult);
    }

    @RequestMapping("getRecordResult")
    public List<RecordResult> getRecordResult(RecordResult recordResult){
        return emrRecordService.getRecordResult(recordResult);
    }
}
