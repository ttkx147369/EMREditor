package com.emreditor.beans;

import java.util.Date;

public class Page implements java.io.Serializable {

    /**
     * 病历主表
     */
    private static final long serialVersionUID = 1L;
    //页面id
    private String idpage;
    //页面更新时间
    private Date page_update;
    // 页面类型
    // 1：信息展示页，不包含表单字段，
    // 2：普通表单填写页面，不包含任何计算和评估结果，
    // 3：简单评估表单页面（包含计算公式），只有一个结果或多个结果，结果包含在表单项中，
    // 4：复杂评估表单页面，包含多个结果，评估结果不仅仅在表单项中，还包含其他内容
    private String page_type;
    //页面标题
    private String page_title;
    //页面创建时间
    private Date page_create;
    //页面背景图
    private String page_backimg;
    //字体样式
    private String textcss;
    //内容样式
    private String cssstyle;

    private String pagecol5;
    private String pagecol4;
    private String pagecol3;
    private String pagecol2;
    private String pagecol1;
    private String pagecol;

    public String getIdpage() {
        return idpage;
    }

    public void setIdpage(String idpage) {
        this.idpage = idpage;
    }

    public String getPagecol5() {
        return pagecol5;
    }

    public void setPagecol5(String pagecol5) {
        this.pagecol5 = pagecol5;
    }

    public String getPagecol4() {
        return pagecol4;
    }

    public void setPagecol4(String pagecol4) {
        this.pagecol4 = pagecol4;
    }

    public String getPagecol3() {
        return pagecol3;
    }

    public void setPagecol3(String pagecol3) {
        this.pagecol3 = pagecol3;
    }

    public String getPagecol2() {
        return pagecol2;
    }

    public void setPagecol2(String pagecol2) {
        this.pagecol2 = pagecol2;
    }

    public String getPagecol1() {
        return pagecol1;
    }

    public void setPagecol1(String pagecol1) {
        this.pagecol1 = pagecol1;
    }

    public String getPagecol() {
        return pagecol;
    }

    public void setPagecol(String pagecol) {
        this.pagecol = pagecol;
    }

    public Date getPage_update() {
        return page_update;
    }

    public void setPage_update(Date page_update) {
        this.page_update = page_update;
    }

    public String getPage_type() {
        return page_type;
    }

    public void setPage_type(String page_type) {
        this.page_type = page_type;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public Date getPage_create() {
        return page_create;
    }

    public void setPage_create(Date page_create) {
        this.page_create = page_create;
    }

    public String getPage_backimg() {
        return page_backimg;
    }

    public void setPage_backimg(String page_backimg) {
        this.page_backimg = page_backimg;
    }

    public String getTextcss() {
        return textcss;
    }

    public void setTextcss(String textcss) {
        this.textcss = textcss;
    }

    public String getCssstyle() {
        return cssstyle;
    }

    public void setCssstyle(String cssstyle) {
        this.cssstyle = cssstyle;
    }
}