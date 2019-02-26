package com.emreditor.dao;

import com.emreditor.beans.Record;
import com.emreditor.beans.RecordValue;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmrRecordDao {
    /**
     * 条件病历记录信息
     *
     * @param record 1
     * @return 1
     */
    @SelectProvider(type = RecordMapperProvider.class, method = "drecord")
    List<Record> drecord(Record record);

    /**
     * 新增电子病历记录
     *
     * @param record 1
     * @return 1
     */
    @InsertProvider(type = RecordMapperProvider.class, method = "insetrecord")
    int insetrecord(Record record);

    /**
     * 更新记录主表信息
     *
     * @param record 1
     * @return 1
     */
    @UpdateProvider(type = RecordMapperProvider.class, method = "updRecord")
    int updRecord(Record record);

    /**
     * 删除记录详细信息
     *
     * @param recordValue 1
     * @return 1
     */
    @DeleteProvider(type = RecordMapperProvider.class, method = "deleteRecordValue")
    int deleteRecordValue(RecordValue recordValue);

    /**
     * 新增记录详情信息
     *
     * @param recordValue 1
     * @return 1
     */
    @InsertProvider(type = RecordMapperProvider.class, method = "insertRecordValue")
    int insertRecordValue(RecordValue recordValue);

    /**
     * 条件查询详情记录表
     *
     * @param recordValue 1
     * @return 1
     */
    @SelectProvider(type = RecordMapperProvider.class, method = "getRecordValue")
    List<RecordValue> getRecordValue(RecordValue recordValue);

    @DeleteProvider(type = RecordMapperProvider.class, method = "deleterecord")
    int deleterecord(Record record);

    /**
     * 获取记录表中某一次记录的其中一些参数
     *
     * @param map 1
     * @return 1
     */
    @Select("select idrecord,record_value from (" +
            "select idrecord,record_value,record_ele from emr.record_value where idrecord=#{idrecord}" +
            ") a where record_ele like #{namel} or record_ele=#{nameb}")
    List<Map<String, Object>> getPartRecordValue(Map<String, String> map);
}
