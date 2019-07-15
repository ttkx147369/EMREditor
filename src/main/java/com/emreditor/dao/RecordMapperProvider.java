package com.emreditor.dao;

import com.emreditor.beans.Record;
import com.emreditor.beans.RecordResult;
import com.emreditor.beans.RecordValue;
import org.apache.ibatis.jdbc.SQL;

public class RecordMapperProvider extends SQL {

    /**
     * 条件查询主记录表
     *
     * @param record 1
     * @return 1
     */
    public String drecord(Record record) {
        SELECT("*");
        FROM("record");
        if (record.getIdpage() != null && record.getIdpage().length() > 0)
            WHERE("idpage=#{idpage}");
        if (record.getIdrecord() != null && record.getIdrecord().length() > 0)
            WHERE("idrecord=#{idrecord}");
        if (record.getPage_backimg() != null && record.getPage_backimg().length() > 0)
            WHERE("page_backimg=#{page_backimg}");
        if (record.getRecord_create() != null)
            WHERE("record_create=#{record_create}");
        if (record.getRecord_title() != null && record.getRecord_title().length() > 0)
            WHERE("record_title like CONCAT('%',#{record_title},'%'");
        if (record.getRecord_update() != null)
            WHERE("record_update=#{record_update}");
        if (record.getRecord_state() != null && record.getRecord_state().length() > 0)
            WHERE("record_state=#{record_state}");
        if (record.getRecordcol() != null && record.getRecordcol().length() > 0)
            WHERE("recordcol=#{recordcol}");
        if (record.getRecordcol1() != null && record.getRecordcol1().length() > 0)
            WHERE("recordcol1=#{recordcol1}");
        if (record.getRecordcol2() != null && record.getRecordcol2().length() > 0)
            WHERE("recordcol2=#{recordcol2}");
        if (record.getRecordcol3() != null && record.getRecordcol3().length() > 0)
            WHERE("recordcol3=#{recordcol3}");
        if (record.getRecordcol4() != null && record.getRecordcol4().length() > 0)
            WHERE("recordcol4=#{recordcol4}");
        if (record.getRecordcol5() != null && record.getRecordcol5().length() > 0)
            WHERE("recordcol5=#{recordcol5}");
        return toString();
    }

    /**
     * 构建插入数据
     *
     * @param record 1
     * @return 1
     */
    public String insetrecord(Record record) {
        INSERT_INTO("record");
        if (record.getIdpage() != null && record.getIdpage().length() > 0)
            VALUES("idpage", "#{idpage}");
        if (record.getIdrecord() != null && record.getIdrecord().length() > 0)
            VALUES("idrecord", "#{idrecord}");
        if (record.getPage_backimg() != null && record.getPage_backimg().length() > 0)
            VALUES("page_backimg", "#{page_backimg}");
        if (record.getRecord_create() != null)
            VALUES("record_create", "#{record_create}");
        if (record.getRecord_title() != null && record.getRecord_title().length() > 0)
            VALUES("record_title", "#{record_title}");
        if (record.getRecord_update() != null)
            VALUES("record_update", "#{record_update}");
        if (record.getRecord_state() != null && record.getRecord_state().length() > 0)
            VALUES("record_state", "#{record_state}");
        if (record.getRecordcol() != null && record.getRecordcol().length() > 0)
            VALUES("recordcol", "#{recordcol}");
        if (record.getRecordcol1() != null && record.getRecordcol1().length() > 0)
            VALUES("recordcol1", "#{recordcol1}");
        if (record.getRecordcol2() != null && record.getRecordcol2().length() > 0)
            VALUES("recordcol2", "#{recordcol2}");
        if (record.getRecordcol3() != null && record.getRecordcol3().length() > 0)
            VALUES("recordcol3", "#{recordcol3}");
        if (record.getRecordcol4() != null && record.getRecordcol4().length() > 0)
            VALUES("recordcol4", "#{recordcol4}");
        if (record.getRecordcol5() != null && record.getRecordcol5().length() > 0)
            VALUES("recordcol5", "#{recordcol5}");
        return toString();
    }

    /**
     * 更新记录主表信息
     *
     * @param record 1
     * @return 1
     */
    public String updRecord(Record record) {
        return new SQL() {
            {
                UPDATE("record");
                if (record.getRecord_title() != null && record.getRecord_title().length() > 0)
                    SET("record_title=#{record_title}");
                if (record.getRecord_update() != null)
                    SET("record_update=#{record_update}");
                if (record.getRecord_state() != null && record.getRecord_state().length() > 0)
                    SET("record_state=#{record_state}");
                if (record.getRecordcol() != null && record.getRecordcol().length() > 0)
                    SET("recordcol=#{recordcol}");
                if (record.getRecordcol1() != null && record.getRecordcol1().length() > 0)
                    SET("recordcol1=#{recordcol1}");
                if (record.getRecordcol2() != null && record.getRecordcol2().length() > 0)
                    SET("recordcol2=#{recordcol2}");
                if (record.getRecordcol3() != null && record.getRecordcol3().length() > 0)
                    SET("recordcol3=#{recordcol3}");
                if (record.getRecordcol4() != null && record.getRecordcol4().length() > 0)
                    SET("recordcol4=#{recordcol4}");
                if (record.getRecordcol5() != null && record.getRecordcol5().length() > 0)
                    SET("recordcol5=#{recordcol5}");
                WHERE("idrecord=#{idrecord}");
            }
        }.toString();
    }

    /**
     * 删除记录详细信息
     *
     * @param stringBuilder 1
     * @return 1
     */
    public String deleteRecordValue(StringBuilder stringBuilder) {
        return stringBuilder.toString();
    }

    /**
     * 插入评估详细记录
     *
     * @param recordValue 1
     * @return 1
     */
    public String insertRecordValue(StringBuilder recordValue) {
        return recordValue.toString();
    }
    /**
     * 更新评估详细记录
     *
     * @param recordValue 1
     * @return 1
     */
    public String updateRecordValue(StringBuilder recordValue) {
        return recordValue.toString();
    }
    /**
     * 条件查询详情记录表
     *
     * @param sql 1
     * @return 1
     */
    public String getRecordValue(StringBuilder sql) {
        return sql.toString();
    }

    public String deleterecord(Record record) {
        DELETE_FROM("record");
        if (record.getIdpage() != null && record.getIdpage().length() > 0)
            WHERE("idpage=#{idpage}");
        if (record.getIdrecord() != null && record.getIdrecord().length() > 0)
            WHERE("idrecord=#{idrecord}");
        if (record.getPage_backimg() != null && record.getPage_backimg().length() > 0)
            WHERE("page_backimg=#{page_backimg}");
        if (record.getRecord_create() != null)
            WHERE("record_create=#{record_create}");
        if (record.getRecord_title() != null && record.getRecord_title().length() > 0)
            WHERE("record_title=#{record_title}");
        if (record.getRecord_update() != null)
            WHERE("record_update=#{record_update}");
        if (record.getRecord_state() != null && record.getRecord_state().length() > 0)
            WHERE("record_state=#{record_state}");
        if (record.getRecordcol() != null && record.getRecordcol().length() > 0)
            WHERE("recordcol=#{recordcol}");
        if (record.getRecordcol1() != null && record.getRecordcol1().length() > 0)
            WHERE("recordcol1=#{recordcol1}");
        if (record.getRecordcol2() != null && record.getRecordcol2().length() > 0)
            WHERE("recordcol2=#{recordcol2}");
        if (record.getRecordcol3() != null && record.getRecordcol3().length() > 0)
            WHERE("recordcol3=#{recordcol3}");
        if (record.getRecordcol4() != null && record.getRecordcol4().length() > 0)
            WHERE("recordcol4=#{recordcol4}");
        if (record.getRecordcol5() != null && record.getRecordcol5().length() > 0)
            WHERE("recordcol5=#{recordcol5}");
        return toString();
    }

    public String saveRecordValue(RecordResult recordResult){
        INSERT_INTO("record_result");
        if (recordResult.getIdrecord_result() != null && recordResult.getIdrecord_result().length() > 0)
            VALUES("idrecord_result","#{idrecord_result}");
        if (recordResult.getIdpage() != null && recordResult.getIdpage().length() > 0)
            VALUES("idpage", "#{idpage}");
        if (recordResult.getIdrecord() != null && recordResult.getIdrecord().length() > 0)
            VALUES("idrecord", "#{idrecord}");
        if (recordResult.getRecord_result() != null && recordResult.getRecord_result().length() > 0)
            VALUES("record_result", "#{record_result}");
        if (recordResult.getBak1() != null && recordResult.getBak1().length() > 0)
            VALUES("bak1", "#{bak1}");
        if (recordResult.getBak2() != null && recordResult.getBak2().length() > 0)
            VALUES("bak2", "#{bak2}");
        if (recordResult.getBak3() != null && recordResult.getBak3().length() > 0)
            VALUES("bak3", "#{bak3}");
        return toString();
    }

    public String getRecordResult(RecordResult recordResult){
        SELECT("*");
        FROM("record_result");
        if (recordResult.getIdrecord_result() != null && recordResult.getIdrecord_result().length() > 0)
            WHERE("idrecord_result=#{idrecord_result}");
        if (recordResult.getIdpage() != null && recordResult.getIdpage().length() > 0)
            WHERE("idpage=#{idpage}");
        if (recordResult.getIdrecord() != null && recordResult.getIdrecord().length() > 0)
            WHERE("idrecord=#{idrecord}");
        //if (recordResult.getRecord_result() != null && recordResult.getRecord_result().length() > 0)
        //    WHERE("record_result=#{record_result}");
        if (recordResult.getBak1() != null && recordResult.getBak1().length() > 0)
            WHERE("bak1=#{bak1}");
        if (recordResult.getBak2() != null && recordResult.getBak2().length() > 0)
            WHERE("bak2=#{bak2}");
        if (recordResult.getBak3() != null && recordResult.getBak3().length() > 0)
            WHERE("bak3=#{bak3}");
        return toString();
    }
}