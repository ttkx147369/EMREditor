package com.emreditor.beans;

import java.util.List;

public class Page_ele {
    private String idele;//主键
    private String idpage;//页面主表主键
    private String ele_type;//页面元素类型（1：分类标题，2：label，3：输入框，4：下拉框，5：单选框，6：多选框，7：文本域，8：文件上传，9：说明性文字）
    private String ele_conn;//下拉框，单选，多选框选择数据来源接口
    private String ele_value;//下拉框，单选，多选框选择数据固定值，json类型{key:value,key1:value1}
    private String limit_type;//限制类型（1：数值大小，2：字符长度，3：不能包含字符）
    private String limit_length;//输入值输入长度
    private String limit_range;//输入值显示范围a>10 and a<20的形式
    private String limit_char;//输入值中不能包含的字符（需用正则表达式，匹配不需要包含的内容）
    private String occupy_col;//占用列
    private String occupy_row;//占用行
    private Integer show_seq;//显示顺序
    private String label;//显示文字
    private String textcss;//字体样式
    private String cssstyle;//内容样式
    private String ele_id;//页面元素id，只能是小写字母
    private List<Page_table> page_tables;

    public String getIdele() {
        return idele;
    }

    public void setIdele(String idele) {
        this.idele = idele;
    }

    public String getIdpage() {
        return idpage;
    }

    public void setIdpage(String idpage) {
        this.idpage = idpage;
    }

    public String getEle_type() {
        return ele_type;
    }

    public void setEle_type(String ele_type) {
        this.ele_type = ele_type;
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

    public String getLimit_type() {
        return limit_type;
    }

    public void setLimit_type(String limit_type) {
        this.limit_type = limit_type;
    }

    public String getLimit_length() {
        return limit_length;
    }

    public void setLimit_length(String limit_length) {
        this.limit_length = limit_length;
    }

    public String getLimit_range() {
        return limit_range;
    }

    public void setLimit_range(String limit_range) {
        this.limit_range = limit_range;
    }

    public String getLimit_char() {
        return limit_char;
    }

    public void setLimit_char(String limit_char) {
        this.limit_char = limit_char;
    }

    public String getOccupy_col() {
        return occupy_col;
    }

    public void setOccupy_col(String occupy_col) {
        this.occupy_col = occupy_col;
    }

    public String getOccupy_row() {
        return occupy_row;
    }

    public void setOccupy_row(String occupy_row) {
        this.occupy_row = occupy_row;
    }

    public Integer getShow_seq() {
        return show_seq;
    }

    public void setShow_seq(Integer show_seq) {
        this.show_seq = show_seq;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getEle_id() {
        return ele_id;
    }

    public void setEle_id(String ele_id) {
        this.ele_id = ele_id;
    }

    public List<Page_table> getPage_tables() {
        return page_tables;
    }

    public void setPage_tables(List<Page_table> page_tables) {
        this.page_tables = page_tables;
    }
}
