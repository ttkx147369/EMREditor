package com.emreditor.dao;

import com.emreditor.beans.Record;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmrRecordDao {

    @InsertProvider(type = RecordMapperProvider.class, method = "insetrecord")
    int insetrecord(Record record);
}
