package com.emreditor.service;

import com.emreditor.beans.Page;
import com.emreditor.beans.Record;
import com.emreditor.beans.RecordValue;
import com.emreditor.dao.EmrPageDao;
import com.emreditor.dao.EmrRecordDao;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class EmrRecordService {
    @Autowired
    private EmrRecordDao emrRecordDao;
    @Autowired
    private EmrPageDao emrPageDao;

    public int insertRecord(HttpServletRequest request, Record record, RecordValue recordValue) {
        String recordId = null;
        if (record.getIdrecord() == null || record.getIdrecord().length() == 0) {
            recordId = UUID.randomUUID().toString();
            record.setIdrecord(recordId);
        } else {
            recordId = record.getIdrecord();

            emrRecordDao.insetrecord(record);
        }
        return 0;
    }
}
