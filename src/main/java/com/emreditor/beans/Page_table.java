package com.emreditor.beans;

public class Page_table {
    private String idtable;//主键
    private String idele;//page_ele表主键
    private Integer colspan;//td中的colspan属性
    private Integer rowspan;//td中的rowspan属性值
    private String label_type;//标签类型（td or tr）
    private String showcontent;//显示内容，里面有input等内容都是直接存label string
    private Integer show_seq;//显示顺序
    private Integer tdrow;//第几行
    private Integer tdcol;//一行中的第几列
    private String textcss;//td的文本样式
    private String cssstyle;//td的css样式
    private String ele_type;//页面元素类型（1：分类标题，2：label，3：输入框，4：下拉框，5：单选框，6：多选框，7：文本域，8：文件上传，9：说明性文字）
    private String ele_id;//页面元素id，只能是小写字母
    private String ele_conn;//下拉框，单选，多选框选择数据来源接口
    private String ele_value;//下拉框，单选，多选框选择数据固定值，json类型{key:value,key1:value1}


    public String getIdtable() {
        return idtable;
    }

    public void setIdtable(String idtable) {
        this.idtable = idtable;
    }

    public String getIdele() {
        return idele;
    }

    public void setIdele(String idele) {
        this.idele = idele;
    }

    public Integer getColspan() {
        return colspan;
    }

    public void setColspan(Integer colspan) {
        this.colspan = colspan;
    }

    public Integer getRowspan() {
        return rowspan;
    }

    public void setRowspan(Integer rowspan) {
        this.rowspan = rowspan;
    }

    public String getLabel_type() {
        return label_type;
    }

    public void setLabel_type(String label_type) {
        this.label_type = label_type;
    }

    public String getShowcontent() {
        return showcontent;
    }

    public void setShowcontent(String showcontent) {
        this.showcontent = showcontent;
    }

    public Integer getShow_seq() {
        return show_seq;
    }

    public void setShow_seq(Integer show_seq) {
        this.show_seq = show_seq;
    }

    public Integer getTdrow() {
        return tdrow;
    }

    public void setTdrow(Integer tdrow) {
        this.tdrow = tdrow;
    }

    public Integer getTdcol() {
        return tdcol;
    }

    public void setTdcol(Integer tdcol) {
        this.tdcol = tdcol;
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
}
