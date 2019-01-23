package com.emreditor.dao;

import com.emreditor.beans.Page;
import com.emreditor.beans.Page_ele;
import com.emreditor.beans.Page_table;
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
     * 合并单元格是先删除多余的单元格
     * @param map scol srow idele ecol erow
     * @return 1
     */
    @Delete("delete from page_table where idele=#{idele} and tdrow>=#{srow} and tdrow<=#{erow} and tdcol>=#{scol} and tdcol<=#{ecol} " +
            "and idtable not in (select idtable from(" +
            "select idtable idtable from page_table where idele=#{idele} and tdrow=#{srow} and tdcol=#{scol}" +
            ") as n)")
    int tdMergeDelTd(Map<String, String> map);

    /**
     * 单元格合并更新第一个单元格的colspan和rowspan
     * @param map scol srow idele ecol erow col row
     * @return 1
     */
    @Update("update page_table set colspan=#{col},rowspan=#{row} where idele=#{idele} and tdrow=#{srow} and tdcol=#{scol}")
    int tdMergeUpdTd(Map<String, String> map);

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

    /**
     * 条件查询单元格信息
     * @param page_table 1
     * @return 1
     */
    @SelectProvider(type = PageMapperProvider.class, method = "getTableCol")
    List<Page_table> getTableCol(Page_table page_table);
}