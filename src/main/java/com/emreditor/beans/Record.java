package com.emreditor.beans;

import java.util.Date;

public class Record {
    private String idpage;//页面配置主表id
    private String idrecord;//主键
    private String page_backimg;//病历背景图，不重复，居左上角显示
    private Date record_create;//创建时间
    private String record_title;//标题
    private Date record_update;//修改时间
    private String record_state;//记录状态（-1：新建未填写，0：填写只保存未提交，1：提交）
    private String recordcol;//备用
    private String recordcol1;//备用
    private String recordcol2;//备用
    private String recordcol3;//备用
    private String recordcol4;//备用
    private String recordcol5;//备用

    public String getIdpage() {
        return idpage;
    }

    public void setIdpage(String idpage) {
        this.idpage = idpage;
    }

    public String getIdrecord() {
        return idrecord;
    }

    public void setIdrecord(String idrecord) {
        this.idrecord = idrecord;
    }

    public String getPage_backimg() {
        return page_backimg;
    }

    public void setPage_backimg(String page_backimg) {
        this.page_backimg = page_backimg;
    }

    public Date getRecord_create() {
        return record_create;
    }

    public void setRecord_create(Date record_create) {
        this.record_create = record_create;
    }

    public String getRecord_title() {
        return record_title;
    }

    public void setRecord_title(String record_title) {
        this.record_title = record_title;
    }

    public Date getRecord_update() {
        return record_update;
    }

    public void setRecord_update(Date record_update) {
        this.record_update = record_update;
    }

    public String getRecord_state() {
        return record_state;
    }

    public void setRecord_state(String record_state) {
        this.record_state = record_state;
    }

    public String getRecordcol() {
        return recordcol;
    }

    public void setRecordcol(String recordcol) {
        this.recordcol = recordcol;
    }

    public String getRecordcol1() {
        return recordcol1;
    }

    public void setRecordcol1(String recordcol1) {
        this.recordcol1 = recordcol1;
    }

    public String getRecordcol2() {
        return recordcol2;
    }

    public void setRecordcol2(String recordcol2) {
        this.recordcol2 = recordcol2;
    }

    public String getRecordcol3() {
        return recordcol3;
    }

    public void setRecordcol3(String recordcol3) {
        this.recordcol3 = recordcol3;
    }

    public String getRecordcol4() {
        return recordcol4;
    }

    public void setRecordcol4(String recordcol4) {
        this.recordcol4 = recordcol4;
    }

    public String getRecordcol5() {
        return recordcol5;
    }

    public void setRecordcol5(String recordcol5) {
        this.recordcol5 = recordcol5;
    }
}
