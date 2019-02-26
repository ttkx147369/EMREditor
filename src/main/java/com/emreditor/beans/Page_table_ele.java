package com.emreditor.beans;

public class Page_table_ele {
    private String idpage_table_ele;
    private String page_table_id;
    private String ele_type;
    private String ele_id;
    private String ele_conn;
    private String ele_value;
    private String calculate;//计算公式

    public String getIdpage_table_ele() {
        return idpage_table_ele;
    }

    public void setIdpage_table_ele(String idpage_table_ele) {
        this.idpage_table_ele = idpage_table_ele;
    }

    public String getPage_table_id() {
        return page_table_id;
    }

    public void setPage_table_id(String page_table_id) {
        this.page_table_id = page_table_id;
    }

    public String getEle_type() {
        return ele_type;
    }

    public void setEle_type(String ele_type) {
        this.ele_type = ele_type;
    }

    public String getEle_id() {
        return ele_id;
    }

    public void setEle_id(String ele_id) {
        this.ele_id = ele_id;
    }

    public String getEle_conn() {
        return ele_conn;
    }

    public void setEle_conn(String ele_conn) {
        this.ele_conn = ele_conn;
    }

    public String getEle_value() {
        return ele_value;
    }

    public void setEle_value(String ele_value) {
        this.ele_value = ele_value;
    }

    public String getCalculate() {
        return calculate;
    }

    public void setCalculate(String calculate) {
        this.calculate = calculate;
    }
}
