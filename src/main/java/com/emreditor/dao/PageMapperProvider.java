package com.emreditor.dao;

import com.emreditor.beans.Page;
import com.emreditor.beans.Page_ele;
import com.emreditor.beans.Page_table;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class PageMapperProvider {
    /**
     * 生成查询语句
     *
     * @param page 1
     * @return 1
     */
    public String getDbType(Page page) {
        return new SQL() {
            {
                SELECT("*");
                FROM("page");
                if (page.getIdpage() != null && page.getIdpage().length() > 0)
                    WHERE("idpage = #{idpage}");
                if (page.getPage_type() != null && page.getPage_type().length() > 0)
                    WHERE("page_type = #{idpage}");
                if (page.getPage_title() != null && page.getPage_title().length() > 0)
                    WHERE("page_title like '%'||#{page_title}||'%'");
            }
        }.toString();
    }

    /**
     * 生成新增语句
     *
     * @return 1
     */
    public String addDbType(Page page) {
        return new SQL() {
            {
                INSERT_INTO("page");
                if (page.getIdpage() != null && page.getIdpage().length() > 0)
                    VALUES("idpage", "#{idpage}");
                if (page.getPage_title() != null && page.getPage_title().length() > 0)
                    VALUES("page_title", "#{page_title}");
                if (page.getPage_type() != null && page.getPage_type().length() > 0)
                    VALUES("page_type", "#{page_type}");
                if (page.getPage_create() != null)
                    VALUES("page_create", "#{page_create}");
                if (page.getPage_update() != null)
                    VALUES("page_update", "#{page_update}");
                if (page.getPage_backimg() != null && page.getPage_backimg().length() > 0)
                    VALUES("page_backimg", "#{page_backimg}");
                if (page.getTextcss() != null && page.getTextcss().length() > 0)
                    VALUES("textcss", "#{textcss}");
                if (page.getCssstyle() != null && page.getCssstyle().length() > 0)
                    VALUES("cssstyle", "#{cssstyle}");
                if (page.getPagecol() != null && page.getPagecol().length() > 0)
                    VALUES("pagecol", "#{pagecol}");
                if (page.getPagecol1() != null && page.getPagecol1().length() > 0)
                    VALUES("pagecol1", "#{pagecol1}");
                if (page.getPagecol2() != null && page.getPagecol2().length() > 0)
                    VALUES("pagecol2", "#{pagecol2}");
                if (page.getPagecol3() != null && page.getPagecol3().length() > 0)
                    VALUES("pagecol3", "#{pagecol3}");
                if (page.getPagecol4() != null && page.getPagecol4().length() > 0)
                    VALUES("pagecol4", "#{pagecol4}");
                if (page.getPagecol5() != null && page.getPagecol5().length() > 0)
                    VALUES("pagecol5", "#{pagecol5}");
            }
        }.toString();
    }

    /**
     * 生成修改语句
     *
     * @return 1
     */
    public String updDbType(Page page) {
        return new SQL() {
            {
                UPDATE("page");
                if (page.getPage_title() != null && page.getPage_title().length() > 0)
                    SET("page_title=#{page_title}");
                if (page.getPage_type() != null && page.getPage_type().length() > 0)
                    SET("page_type=#{page_type}");
                if (page.getPage_create() != null)
                    SET("page_create=#{page_create}");
                if (page.getPage_update() != null)
                    SET("page_update=#{page_update}");
                if (page.getPage_backimg() != null && page.getPage_backimg().length() > 0)
                    SET("page_backimg", "#{page_backimg}");
                if (page.getTextcss() != null && page.getTextcss().length() > 0)
                    SET("textcss=#{textcss}");
                if (page.getCssstyle() != null && page.getCssstyle().length() > 0)
                    SET("cssstyle=#{cssstyle}");
                if (page.getPagecol() != null && page.getPagecol().length() > 0)
                    SET("pagecol=#{pagecol}");
                if (page.getPagecol1() != null && page.getPagecol1().length() > 0)
                    SET("pagecol1=#{pagecol1}");
                if (page.getPagecol2() != null && page.getPagecol2().length() > 0)
                    SET("pagecol2=#{pagecol2}");
                if (page.getPagecol3() != null && page.getPagecol3().length() > 0)
                    SET("pagecol3=#{pagecol3}");
                if (page.getPagecol4() != null && page.getPagecol4().length() > 0)
                    SET("pagecol4=#{pagecol4}");
                if (page.getPagecol5() != null && page.getPagecol5().length() > 0)
                    SET("pagecol5=#{pagecol5}");
                WHERE("idpage = #{idpage}");
            }
        }.toString();
    }

    /**
     * 生成删除语句
     *
     * @return 1
     */
    public String delPageData(Page page) {
        return new SQL() {
            {
                DELETE_FROM("page");
                WHERE("idpage = #{idpage}");
            }
        }.toString();
    }

    /**
     * 生成页面元素项查询语句
     *
     * @return 1
     */
    public String getPageEle(Page_ele page_ele) {
        return new SQL() {
            {
                SELECT("*");
                FROM("page_ele");
                if (page_ele.getIdele() != null && page_ele.getIdele().length() > 0)
                    WHERE("idele=#{idele}");
                if (page_ele.getIdpage() != null && page_ele.getIdpage().length() > 0)
                    WHERE("idpage=#{idpage}");
                if (page_ele.getEle_type() != null && page_ele.getEle_type().length() > 0)
                    WHERE("ele_type=#{ele_type}");
                if (page_ele.getEle_conn() != null && page_ele.getEle_conn().length() > 0)
                    WHERE("ele_conn=#{ele_conn}");
                if (page_ele.getEle_value() != null && page_ele.getEle_value().length() > 0)
                    WHERE("ele_value=#{ele_value}");
                if (page_ele.getLimit_type() != null && page_ele.getLimit_type().length() > 0)
                    WHERE("limit_type=#{limit_type}");
                if (page_ele.getLimit_length() != null && page_ele.getLimit_length().length() > 0)
                    WHERE("limit_length=#{limit_length}");
                if (page_ele.getLimit_range() != null && page_ele.getLimit_range().length() > 0)
                    WHERE("limit_range=#{limit_range}");
                if (page_ele.getLimit_char() != null && page_ele.getLimit_char().length() > 0)
                    WHERE("limit_char=#{limit_char}");
                if (page_ele.getOccupy_col() != null && page_ele.getOccupy_col().length() > 0)
                    WHERE("occupy_col=#{occupy_col}");
                if (page_ele.getOccupy_row() != null && page_ele.getOccupy_row().length() > 0)
                    WHERE("occupy_row=#{occupy_row}");
                if (page_ele.getShow_seq() != null)
                    WHERE("show_seq=#{show_seq}");
                if (page_ele.getTextcss() != null && page_ele.getTextcss().length() > 0)
                    WHERE("textcss=#{textcss}");
                if (page_ele.getCssstyle() != null && page_ele.getCssstyle().length() > 0)
                    WHERE("cssstyle=#{cssstyle}");
                if (page_ele.getLabel() != null && page_ele.getLabel().length() > 0)
                    WHERE("label=#{label}");
                if (page_ele.getEle_id() != null && page_ele.getEle_id().length() > 0)
                    WHERE("ele_id=#{ele_id}");
                ORDER_BY("show_seq");
            }
        }.toString();
    }

    /**
     * 生成页面元素项新增语句
     *
     * @return 1
     */
    public String addPageEle(Page_ele page_ele) {
        return new SQL() {
            {
                INSERT_INTO("page_ele");
                if (page_ele.getIdele() != null && page_ele.getIdele().length() > 0)
                    VALUES("idele", "#{idele}");
                if (page_ele.getIdpage() != null && page_ele.getIdpage().length() > 0)
                    VALUES("idpage", "#{idpage}");
                if (page_ele.getEle_type() != null && page_ele.getEle_type().length() > 0)
                    VALUES("ele_type", "#{ele_type}");
                if (page_ele.getEle_conn() != null && page_ele.getEle_conn().length() > 0)
                    VALUES("ele_conn", "#{ele_conn}");
                if (page_ele.getEle_value() != null && page_ele.getEle_value().length() > 0)
                    VALUES("ele_value", "#{ele_value}");
                if (page_ele.getLimit_type() != null && page_ele.getLimit_type().length() > 0)
                    VALUES("limit_type", "#{limit_type}");
                if (page_ele.getLimit_length() != null && page_ele.getLimit_length().length() > 0)
                    VALUES("limit_length", "#{limit_length}");
                if (page_ele.getLimit_range() != null && page_ele.getLimit_range().length() > 0)
                    VALUES("limit_range", "#{limit_range}");
                if (page_ele.getLimit_char() != null && page_ele.getLimit_char().length() > 0)
                    VALUES("limit_char", "#{limit_char}");
                if (page_ele.getOccupy_col() != null && page_ele.getOccupy_col().length() > 0)
                    VALUES("occupy_col", "#{occupy_col}");
                if (page_ele.getOccupy_row() != null && page_ele.getOccupy_row().length() > 0)
                    VALUES("occupy_row", "#{occupy_row}");
                //if (page_ele.getIdpage() != null && page_ele.getIdpage().length() > 0)
                VALUES("show_seq", "#{show_seq}");
                if (page_ele.getTextcss() != null && page_ele.getTextcss().length() > 0)
                    VALUES("textcss", "#{textcss}");
                if (page_ele.getCssstyle() != null && page_ele.getCssstyle().length() > 0)
                    VALUES("cssstyle", "#{cssstyle}");
                if (page_ele.getLabel() != null && page_ele.getLabel().length() > 0)
                    VALUES("label", "#{label}");
                if (page_ele.getEle_id() != null && page_ele.getEle_id().length() > 0)
                    VALUES("ele_id", "#{ele_id}");
            }
        }.toString();
    }

    /**
     * 生成页面元素项修改语句
     *
     * @return 1
     */
    public String updPageEle(Page_ele page_ele) {
        return new SQL() {
            {
                UPDATE("page_ele");
                if (page_ele.getIdpage() != null && page_ele.getIdpage().length() > 0)
                    SET("idpage=#{idpage}");
                if (page_ele.getEle_type() != null && page_ele.getEle_type().length() > 0)
                    SET("ele_type=#{ele_type}");
                if (page_ele.getEle_conn() != null && page_ele.getEle_conn().length() > 0)
                    SET("ele_conn=#{ele_conn}");
                if (page_ele.getEle_value() != null && page_ele.getEle_value().length() > 0)
                    SET("ele_value=#{ele_value}");
                if (page_ele.getLimit_type() != null && page_ele.getLimit_type().length() > 0)
                    SET("limit_type=#{limit_type}");
                if (page_ele.getLimit_length() != null && page_ele.getLimit_length().length() > 0)
                    SET("limit_length=#{limit_length}");
                if (page_ele.getLimit_range() != null && page_ele.getLimit_range().length() > 0)
                    SET("limit_range=#{limit_range}");
                if (page_ele.getLimit_char() != null && page_ele.getLimit_char().length() > 0)
                    SET("limit_char=#{limit_char}");
                if (page_ele.getOccupy_col() != null && page_ele.getOccupy_col().length() > 0)
                    SET("occupy_col=#{occupy_col}");
                if (page_ele.getOccupy_row() != null && page_ele.getOccupy_row().length() > 0)
                    SET("occupy_row=#{occupy_row}");
                if (page_ele.getShow_seq() != null)
                    SET("show_seq=#{show_seq}");
                if (page_ele.getTextcss() != null && page_ele.getTextcss().length() > 0)
                    SET("textcss=#{textcss}");
                if (page_ele.getCssstyle() != null && page_ele.getCssstyle().length() > 0)
                    SET("cssstyle=#{cssstyle}");
                if (page_ele.getLabel() != null && page_ele.getLabel().length() > 0)
                    SET("label=#{label}");
                if (page_ele.getEle_id() != null && page_ele.getEle_id().length() > 0)
                    SET("ele_id=#{ele_id}");
                WHERE("idele = #{idele}");
            }
        }.toString();
    }

    /**
     * 生成页面元素项删除语句
     *
     * @return 1
     */
    public String delPageEle(Page_ele page_ele) {
        return new SQL() {
            {
                DELETE_FROM("page_ele");
                WHERE("idele = #{idele}");
            }
        }.toString();
    }

    /**
     * 更新元素显示顺序
     *
     * @param map 1
     * @return 1
     */
    public String updseq(Map<String, String> map) {
        return new SQL() {
            {
                UPDATE("page_ele");
                if (Integer.parseInt(map.get("oldseq")) < Integer.parseInt(map.get("newseq"))) {
                    SET("show_seq=show_seq-1");
                    WHERE("show_seq>#{oldseq} and show_seq<=#{newseq} and idpage=#{idpage}");
                } else {
                    SET("show_seq=show_seq+1");
                    WHERE("show_seq>=#{newseq} and show_seq<#{oldseq} and idpage=#{idpage}");
                }
            }
        }.toString();
    }

    public String updseq1(Map<String, String> map) {
        return new SQL() {
            {
                UPDATE("page_ele");
                //idpage oldseq newseq oldid newid
                if (Integer.parseInt(map.get("oldseq")) < Integer.parseInt(map.get("newseq"))) {
                    SET("show_seq=#{newseq}");
                    WHERE("idele=#{oldid}");
                } else {
                    SET("show_seq=#{newseq}");
                    WHERE("idele=#{oldid}");
                }
            }
        }.toString();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////         表格操作部分        //////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 添加table详情数据（注解没有批处理功能）
     *
     * @param page_table 新增字段
     * @return 执行语句
     */
    public String createTableRowCol(Page_table page_table) {
        return new SQL() {
            {
                INSERT_INTO("page_table");
                if (page_table.getIdtable() != null && page_table.getIdtable().length() > 0)
                    VALUES("idtable", "#{idtable}");
                if (page_table.getIdele() != null && page_table.getIdele().length() > 0)
                    VALUES("idele", "#{idele}");
                if (page_table.getColspan() != null)
                    VALUES("colspan", "#{colspan}");
                if (page_table.getRowspan() != null)
                    VALUES("rowspan", "#{rowspan}");
                if (page_table.getLabel_type() != null && page_table.getLabel_type().length() > 0)
                    VALUES("label_type", "#{label_type}");
                if (page_table.getShowcontent() != null && page_table.getShowcontent().length() > 0)
                    VALUES("showcontent", "#{showcontent}");
                if (page_table.getShow_seq() != null)
                    VALUES("show_seq", "#{show_seq}");
                if (page_table.getTdrow() != null)
                    VALUES("tdrow", "#{tdrow}");
                if (page_table.getTdcol() != null)
                    VALUES("tdcol", "#{tdcol}");
                if (page_table.getTextcss() != null && page_table.getTextcss().length() > 0)
                    VALUES("textcss", "#{textcss}");
                if (page_table.getCssstyle() != null && page_table.getCssstyle().length() > 0)
                    VALUES("cssstyle", "#{cssstyle}");
            }
        }.toString();
    }

    /**
     * \
     * 根据page_table获取table详情信息
     *
     * @param page_table 查询条件
     * @return 执行语句
     */
    public String getPageTable(Page_table page_table) {
        return new SQL() {
            {
                SELECT("idtable,idele,colspan,rowspan,label_type,showcontent,show_seq,tdrow,tdcol,textcss,cssstyle,ele_type,ele_id,ele_conn,ele_value");
                FROM("page_table");
                if (page_table.getIdtable() != null && page_table.getIdtable().length() > 0)
                    WHERE("idtable=#{idtable}");
                if (page_table.getIdele() != null && page_table.getIdele().length() > 0)
                    WHERE("idele=#{idele}");
                if (page_table.getColspan() != null)
                    WHERE("colspan=#{colspan}");
                if (page_table.getRowspan() != null)
                    WHERE("rowspan=#{rowspan}");
                if (page_table.getLabel_type() != null && page_table.getLabel_type().length() > 0)
                    WHERE("label_type=#{label_type}");
                if (page_table.getShowcontent() != null && page_table.getShowcontent().length() > 0)
                    WHERE("showcontent=#{showcontent}");
                if (page_table.getShow_seq() != null)
                    WHERE("show_seq=#{show_seq}");
                if (page_table.getTdrow() != null)
                    VALUES("tdrow", "#{tdrow}");
                if (page_table.getTdcol() != null)
                    VALUES("tdcol", "#{tdcol}");
                if (page_table.getTextcss() != null && page_table.getTextcss().length() > 0)
                    WHERE("textcss=#{textcss}");
                if (page_table.getCssstyle() != null && page_table.getCssstyle().length() > 0)
                    WHERE("cssstyle=#{cssstyle}");
                ORDER_BY("tdrow", "tdcol");
            }
        }.toString();
    }

    /**
     * 根据page_table有的值进行删除操作语句
     *
     * @param page_table 删除条件
     * @return 执行语句
     */
    public String delPageTable(Page_table page_table) {
        return new SQL() {
            {
                DELETE_FROM("page_table");
                if (page_table.getIdtable() != null && page_table.getIdtable().length() > 0)
                    WHERE("idtable=#{idtable}");
                if (page_table.getIdele() != null && page_table.getIdele().length() > 0)
                    WHERE("idele=#{idele}");
                if (page_table.getColspan() != null)
                    WHERE("colspan=#{colspan}");
                if (page_table.getRowspan() != null)
                    WHERE("rowspan=#{rowspan}");
                if (page_table.getLabel_type() != null && page_table.getLabel_type().length() > 0)
                    WHERE("label_type=#{label_type}");
                if (page_table.getShowcontent() != null && page_table.getShowcontent().length() > 0)
                    WHERE("showcontent=#{showcontent}");
                if (page_table.getShow_seq() != null)
                    WHERE("show_seq=#{show_seq}");
                if (page_table.getTdrow() != null)
                    VALUES("tdrow", "#{tdrow}");
                if (page_table.getTdcol() != null)
                    VALUES("tdcol", "#{tdcol}");
                if (page_table.getTextcss() != null && page_table.getTextcss().length() > 0)
                    WHERE("textcss=#{textcss}");
                if (page_table.getCssstyle() != null && page_table.getCssstyle().length() > 0)
                    WHERE("cssstyle=#{cssstyle}");
            }
        }.toString();
    }

    /**
     * 插入行或列
     *
     * @param page_table 1
     * @return 1
     */
    public String insetRowCol(Page_table page_table) {
        return new SQL() {
            {
                UPDATE("page_table");
                if (page_table.getTdrow() != null) {
                    SET("tdrow=tdrow+1");
                    WHERE("tdrow>=#{tdrow}");
                }
                if (page_table.getTdcol() != null) {
                    SET("tdcol=tdcol+1");
                    WHERE("tdcol>=#{tdcol}");
                }
                WHERE("idele=#{idele}");
            }
        }.toString();
    }

    /**
     * 删除行或列
     *
     * @param page_table 1
     * @return 1
     */
    public String delRowCol(Page_table page_table) {
        return new SQL() {
            {
                DELETE_FROM("page_table");
                WHERE("idele=#{idele}");
                if (page_table.getTdrow() != null) {
                    WHERE("tdrow=#{tdrow}");
                }
                if (page_table.getTdcol() != null) {
                    WHERE("tdcol=#{tdcol}");
                }
            }
        }.toString();
    }

    /**
     * 删除行或列之后跟新行列信息
     *
     * @param page_table 1
     * @return 1
     */
    public String delUpdRowCol(Page_table page_table) {
        return new SQL() {
            {
                UPDATE("page_table");
                if (page_table.getTdrow() != null) {
                    SET("tdrow=tdrow-1");
                    WHERE("tdrow>=#{tdrow}");
                }
                if (page_table.getTdcol() != null) {
                    SET("tdcol=tdcol-1");
                    WHERE("tdcol>=#{tdcol}");
                }
                WHERE("idele=#{idele}");
            }
        }.toString();
    }

    /**
     * 跟新table元素表相关信息
     * @param page_table 1
     * @return 1
     */
    public String updPageTable(Page_table page_table) {
        return new SQL() {
            {
                UPDATE("page_table");
                if (page_table.getShowcontent() != null && page_table.getShowcontent().length() > 0)
                    SET("showcontent=#{showcontent}");
                if (page_table.getCssstyle() != null && page_table.getCssstyle().length() > 0)
                    SET("cssstyle=#{cssstyle}");
                if (page_table.getTextcss() != null && page_table.getTextcss().length() > 0)
                    SET("textcss=#{textcss}");
                if (page_table.getEle_type() != null && page_table.getEle_type().length() > 0)
                    SET("ele_type=#{ele_type}");
                if (page_table.getEle_id() != null && page_table.getEle_id().length() > 0)
                    SET("ele_id=#{ele_id}");
                if (page_table.getEle_conn() != null && page_table.getEle_conn().length() > 0)
                    SET("ele_conn=#{ele_conn}");
                if (page_table.getEle_value() != null && page_table.getEle_value().length() > 0)
                    SET("ele_value=#{ele_value}");
                WHERE("idtable=#{idtable}");
            }
        }.toString();
    }

    /**
     * 条件查询单元格信息
     * @param page_table 1
     * @return 1
     */
    public String getTableCol(Page_table page_table){
        return new SQL(){
            {
                SELECT("*");
                FROM("page_table");
                if (page_table.getIdtable() != null && page_table.getIdtable().length() > 0)
                    WHERE("idtable=#{idtable}");
                if (page_table.getIdele() != null && page_table.getIdele().length() > 0)
                    WHERE("idele=#{idele}");
                if (page_table.getColspan() != null)
                    WHERE("colspan=#{colspan}");
                if (page_table.getRowspan() != null)
                    WHERE("rowspan=#{rowspan}");
                if (page_table.getLabel_type() != null && page_table.getLabel_type().length() > 0)
                    WHERE("label_type=#{label_type}");
                if (page_table.getShowcontent() != null && page_table.getShowcontent().length() > 0)
                    WHERE("showcontent=#{showcontent}");
                if (page_table.getShow_seq() != null)
                    WHERE("show_seq=#{show_seq}");
                if (page_table.getTdrow() != null)
                    WHERE("tdrow=#{tdrow}");
                if (page_table.getTdcol() != null)
                    WHERE("tdcol=#{tdcol}");
                if (page_table.getTextcss() != null && page_table.getTextcss().length() > 0)
                    WHERE("textcss=#{textcss}");
                if (page_table.getCssstyle() != null && page_table.getCssstyle().length() > 0)
                    WHERE("cssstyle=#{cssstyle}");
                if (page_table.getEle_type() != null && page_table.getEle_type().length() > 0)
                    WHERE("ele_type=#{ele_type}");
                if (page_table.getEle_id() != null && page_table.getEle_id().length() > 0)
                    WHERE("ele_id=#{ele_id}");
                if (page_table.getEle_conn() != null && page_table.getEle_conn().length() > 0)
                    WHERE("ele_conn=#{ele_conn}");
                if (page_table.getEle_value() != null && page_table.getEle_value().length() > 0)
                    WHERE("ele_valuev#{ele_value}");
            }
        }.toString();
    }
}