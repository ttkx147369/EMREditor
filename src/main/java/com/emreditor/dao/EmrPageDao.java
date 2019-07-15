package com.emreditor.dao;

import com.emreditor.beans.Page;
import com.emreditor.beans.Page_ele;
import com.emreditor.beans.Page_table;
import com.emreditor.beans.Page_table_ele;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmrPageDao {

    /**
     * 查询全部
     *
     * @return 查询结果
     */
    @SelectProvider(type = PageMapperProvider.class, method = "getDbType")
    List<Page> getDbType(Page page);

    /**
     * 添加
     *
     * @return 新增记录数
     */
    @InsertProvider(type = PageMapperProvider.class, method = "addDbType")
    int addDbType(Page page);

    /**
     * 更新
     *
     * @return 更新记录数
     */
    @UpdateProvider(type = PageMapperProvider.class, method = "updDbType")
    int updDbType(Page page);

    /**
     * 删除
     *
     * @return 删除记录数
     */
    @DeleteProvider(type = PageMapperProvider.class, method = "delPageData")
    int delPageData(Page page);

    /**
     * 查询页面元素项
     *
     * @return 查询结果数据
     */
    @SelectProvider(type = PageMapperProvider.class, method = "getPageEle")
    List<Page_ele> getPageEle(Page_ele page_ele);

    /**
     * 添加页面元素项
     *
     * @return 添加记录数
     */
    @InsertProvider(type = PageMapperProvider.class, method = "addPageEle")
    int addPageEle(Page_ele page_ele);

    /**
     * 更新页面元素项
     *
     * @return 更新记录数
     */
    @UpdateProvider(type = PageMapperProvider.class, method = "updPageEle")
    int updPageEle(Page_ele page_ele);

    /**
     * 删除页面元素项
     *
     * @return 删除记录数
     */
    @DeleteProvider(type = PageMapperProvider.class, method = "delPageEle")
    int delPageEle(Page_ele page_ele);

    /**
     * 更新元素显示顺序
     *
     * @param map 更新条件
     * @return 更新记录数
     */
    @UpdateProvider(type = PageMapperProvider.class, method = "updseq")
    int updseq(Map<String, String> map);

    @UpdateProvider(type = PageMapperProvider.class, method = "updseq1")
    int updseq1(Map<String, String> map);

    /**
     * 获取pageEle记录总数
     *
     * @param page_ele 查询条件
     * @return 查询总数
     */
    @Select("select count(show_seq) show_seq from page_ele where idpage=#{idpage}")
    Integer getPageEleCount(Page_ele page_ele);

    @Update("UPDATE emr.page_ele a join (select show_seq,idele from emr.page_ele where idele=#{idele}) b " +
            "on a.show_seq>b.show_seq set a.show_seq=a.show_seq-1;")
    Integer updPageEleSwq(Page_ele page_ele);

    /**
     * 新建一个模板新建一个表
     */
    @Select("SELECT count(TABLE_NAME) FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'emr' and table_name=#{idpage}")
    int getEmrTable(Page_ele page_ele);
    @Select("SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS  WHERE TABLE_SCHEMA = 'emr' and TABLE_NAME=#{idpage};")
    List<String> getEmrTableColumn(Page_ele page_ele);
    @UpdateProvider(type = PageMapperProvider.class, method = "createTable")
    int createTable(Page_ele page_ele, List<String> ele_id);
    @UpdateProvider(type = PageMapperProvider.class, method = "alterTable")
    int alterTable(Page_ele page_ele, List<String> ele_id);
    @UpdateProvider(type = PageMapperProvider.class, method = "dropTable")
    int dropTable(String idpage);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////         表格操作部分        //////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 创建表格的行和列
     * (需要换成批处理的方式)
     * @return 行列总数
     */
    @InsertProvider(type = PageMapperProvider.class, method = "createTableRowCol")
    int createTableRowCol(Page_table list);

    /**
     * 根据page_table获取table详情信息
     * @param page_table q
     * @return q
     */
    @SelectProvider(type = PageMapperProvider.class, method = "getPageTable")
    List<Page_table> getPageTable(Page_table page_table);

    @DeleteProvider(type = PageMapperProvider.class, method = "delPageTable")
    int delPageTable(Page_table page_table);

    /**
     * 合并单元格是先设置多余的单元格为隐藏
     * @param map scol srow idele ecol erow formid showcontent
     * @return 1
     */
    @Update("update page_table set tdshow='off',formid=#{formid},showcontent=#{showcontent} " +
            "where idele=#{idele} and tdrow>=#{srow} and tdrow<=#{erow} and tdcol>=#{scol} and tdcol<=#{ecol}")
    int tdMergeTd(Map<String, String> map);

    /**
     * 拆分单元格 将隐藏单元格改为可见，将单元格formid值设置为空
     * @param map row col idele rowspan colspan
     * @return 1
     */
    @Update("update page_table set tdshow='on',colspan=1,rowspan=1,formid=null,showcontent='&nbsp;' " +
            "where idele=#{idele} and tdrow>=#{row} and tdrow<#{rowspan}+#{row} and tdcol>=#{col} and tdcol<#{colspan}+#{col}")
    int tdBreakTd(Map<String, String> map);
    /**
     * 单元格合并更新第一个单元格的colspan和rowspan
     * @param map scol srow idele ecol erow col row
     * @return 1
     */
    @Update("update page_table set colspan=#{col},rowspan=#{row},tdshow='on' where idele=#{idele} and tdrow=#{srow} and tdcol=#{scol}")
    int tdMergeUpdTd(Map<String, String> map);//是否需要修改为显示

    /**
     * 插入行或列
     * @param page_table 1
     * @return 1
     */
    @UpdateProvider(type = PageMapperProvider.class, method = "insetRowCol")
    int insetRowCol(Page_table page_table);

    /**
     * 删除行或列
     * @param page_table 1
     * @return 1
     */
    @UpdateProvider(type = PageMapperProvider.class, method = "delRowCol")
    int delRowCol(Page_table page_table);

    /**
     * 删除行或列之后跟新行列信息
     * @param page_table 1
     * @return 1
     */
    @UpdateProvider(type = PageMapperProvider.class, method = "delUpdRowCol")
    int delUpdRowCol(Page_table page_table);

    @UpdateProvider(type = PageMapperProvider.class, method = "updPageTable")
    int updPageTable(Page_table page_table);

    @Update("update page_table set showcontent=#{showcontent} " +
            "where idele=#{idele} and tdrow>=#{tdrow} and tdrow<#{rowspan}+#{tdrow} and tdcol>=#{tdcol} and tdcol<#{colspan}+#{tdcol}")
    int updPageTableTds(Page_table page_table);

    /**
     * 条件查询单元格信息
     * @param page_table 1
     * @return 1
     */
    @SelectProvider(type = PageMapperProvider.class, method = "getTableCol")
    List<Page_table> getTableCol(Page_table page_table);

    /**
     * 根据idpage查询整个评估表中的所有表单元素name值
     * @param page
     * @return
     */
    @Select("SELECT ele_type,ele_id,calculate FROM emr.page_ele where idpage=#{idpage} and ele_type in(3,4,5,6,7,8) " +
            "union " +
            "select ele_type,ele_id,calculate from emr.page_table_ele where page_table_id in( " +
            "select idtable from emr.page_table where idele in( " +
            "SELECT idele FROM emr.page_ele where idpage=#{idpage} " +
            ") " +
            ") and ele_type in(3,4,5,6,7,8)")
    List<Map> getFormEleName(Page page);
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////         表格td中的表单元素        /////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 更新td中的元素信息
     * @param page_table_ele 1
     * @return 1
     */
    @UpdateProvider(type = PageMapperProvider.class, method = "updTableEle")
    int updTableEle(Page_table_ele page_table_ele);

    /**
     * 新增td中的元素
     * @param page_table_ele 1
     * @return 1
     */
    @InsertProvider(type = PageMapperProvider.class, method = "insetTableEle")
    int insetTableEle(Page_table_ele page_table_ele);

    /**
     * 条件查询td表中的元素
     * @param page_table_ele 1
     * @return 1
     */
    @SelectProvider(type = PageMapperProvider.class, method = "getTableEle")
    List<Page_table_ele> getTableEle(Page_table_ele page_table_ele);

    /**
     * 删除td中的元素
     * @param page_table_ele 1
     * @return 1
     */
    @DeleteProvider(type = PageMapperProvider.class, method = "delTableTdEle")
    int delTableTdEle(Page_table_ele page_table_ele);

    /**
     * 更新td中的formid值
     * @param page_table 1
     */
    @Update("update page_table set formid=#{formid} " +
            "where idele=#{idele} and tdrow>=#{tdrow} and tdrow<#{rowspan}+#{tdrow} and tdcol>=#{tdcol} and tdcol<#{colspan}+#{tdcol}")
    int updPageTableTdFormid(Page_table page_table);

    /**
     * 在单元格合并是，根据合并的行列值，删除单元格中除鼠标按下单元格的所有单元格表单元素
     * @param map
     */
    @Delete("delete from emr.page_table_ele where page_table_id in (" +
            "select a.idtable from emr.page_table as a where idele=#{idele} and tdrow>=#{srow} and tdrow<=#{erow} and tdcol>=#{scol} and tdcol<=#{ecol} " +
            "and idtable!=#{downtdid})")
    int delTableTdEleByRowCol(Map<String, String> map);
    /*update emr.page_table_ele set page_table_id=(select idtable from emr.page_table where idele='f068ed26-6328-4682-a3e1-1c09072c2dc0' and tdrow=1 and tdcol=1)
    where page_table_id='eff9a5d9-e280-4a36-b95b-b1db5b67b733'*/
    @Delete("update emr.page_table_ele set page_table_id=(select idtable from emr.page_table where idele=#{idele} and tdrow=#{srow} and tdcol=#{scol})" +
            " where page_table_id=#{downtdid}")
    int updTableTdEleByRowCol(Map<String, String> map);

    /**
     * 修改一个表格中需要保存值的列
     * @param map 1
     * @return 1
     */
    @Update("update emr.page_table set getvalue='getvalue' where idele=#{idpage} and tdcol in (${cols})")
    int setVlaueColumn(Map<String, String> map);

    /**
     * 修改一个表格中取值的条件
     * @param page_tables 1
     * @return 1
     */
    @Update("update emr.page_table set `condition`=#{condition} where idele=#{idele} and tdrow=#{tdrow} and tdcol='0'")
    int setConditionRow(Page_table page_tables);

    /**
     * 通过页面元素id，获取所有table ele
     * @param p 1
     * @return 1
     */
    @Select("select * from page_table_ele where page_table_id in (SELECT idtable FROM emr.page_table WHERE (idele=#{idele}))")
    List<Page_table_ele> getTableEleByPageid(Page_ele p);
}