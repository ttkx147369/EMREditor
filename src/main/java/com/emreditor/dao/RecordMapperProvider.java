package com.emreditor.dao;

import com.emreditor.beans.Record;
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
     * @param recordValue 1
     * @return 1
     */
    public String deleteRecordValue(RecordValue recordValue) {
        return new SQL() {
            {
                DELETE_FROM("record_value");
                WHERE("idrecord=#{idrecord}");
            }
        }.toString();
    }

    /**
     * 插入评估详细记录
     *
     * @param recordValue 1
     * @return 1
     */
    public String insertRecordValue(RecordValue recordValue) {
        INSERT_INTO("record_value");
        if (recordValue.getIdrecord() != null && recordValue.getIdrecord().length() > 0)
            VALUES("idrecord", "#{idrecord}");
        if (recordValue.getIdrecord_value() != null && recordValue.getIdrecord_value().length() > 0)
            VALUES("idrecord_value", "#{idrecord_value}");
        if (recordValue.getRecord_ele() != null && recordValue.getRecord_ele().length() > 0)
            VALUES("record_ele", "#{record_ele}");
        if (recordValue.getRecord_value() != null && recordValue.getRecord_value().length() > 0)
            VALUES("record_value", "#{record_value}");
        if (recordValue.getRecordcol() != null && recordValue.getRecordcol().length() > 0)
            VALUES("recordcol", "#{recordcol}");
        if (recordValue.getRecordcol1() != null && recordValue.getRecordcol1().length() > 0)
            VALUES("recordcol1", "#{recordcol1}");
        return toString();
    }

    /**
     * 条件查询详情记录表
     *
     * @param recordValue 1
     * @return 1
     */
    public String getRecordValue(RecordValue recordValue) {
        SELECT("*");
        FROM("record_value");
        if (recordValue.getIdrecord() != null && recordValue.getIdrecord().length() > 0)
            WHERE("idrecord=#{idrecord}");
        if (recordValue.getIdrecord_value() != null && recordValue.getIdrecord_value().length() > 0)
            WHERE("idrecord_value=#{idrecord_value}");
        if (recordValue.getRecord_ele() != null && recordValue.getRecord_ele().length() > 0)
            WHERE("record_ele=#{record_ele}");
        if (recordValue.getRecord_value() != null && recordValue.getRecord_value().length() > 0)
            WHERE("record_value=#{record_value}");
        if (recordValue.getRecordcol() != null && recordValue.getRecordcol().length() > 0)
            WHERE("recordcol=#{recordcol}");
        if (recordValue.getRecordcol1() != null && recordValue.getRecordcol1().length() > 0)
            WHERE("recordcol1=#{recordcol1}");
        return toString();
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
}
