package com.emreditor.service;

import com.emreditor.beans.Record;
import com.emreditor.beans.RecordValue;
import com.emreditor.dao.EmrRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class EmrRecordService {

    private EmrRecordDao emrRecordDao;

    @Autowired
    public EmrRecordService(EmrRecordDao emrRecordDao) {
        this.emrRecordDao = emrRecordDao;
    }

    /**
     * 条件查询电子病历记录信息
     *
     * @param record 1
     * @return 1
     */
    public List<Record> drecord(Record record) {
        return emrRecordDao.drecord(record);
    }

    /**
     * 保存电子病历信息
     *
     * @param record 2
     * @return 2
     */
    public String insertRecord(Record record) {
        String idRecord = UUID.randomUUID().toString();
        if (record.getIdrecord() == null || record.getIdrecord().length() == 0) {
            record.setIdrecord(idRecord);
            record.setRecord_create(new Date());
            emrRecordDao.insetrecord(record);
        } else {
            idRecord = record.getIdrecord();
            record.setRecord_update(new Date());
            if(emrRecordDao.updRecord(record)==0){
                record.setRecord_create(new Date());
                emrRecordDao.insetrecord(record);
            }
        }
        return idRecord;
    }

    /**
     * 保存评估详情
     * （需要换成批处理）
     *
     * @param request 1
     * @return 1
     */
    public int insertRecordValue(HttpServletRequest request) {
        int res = 0;
        String idrecord = request.getParameter("idrecord");
        String state = request.getParameter("state");
        Map<String, String[]> map = request.getParameterMap();
        Iterator<String> name = map.keySet().iterator();
        //修改记录状态
        Record record = new Record();
        record.setIdrecord(idrecord);
        record.setRecord_state(state);
        emrRecordDao.updRecord(record);
        //将之前的记录数据删除
        RecordValue recordValue = new RecordValue();
        recordValue.setIdrecord(idrecord);
        emrRecordDao.deleteRecordValue(recordValue);
        while (name.hasNext()) {
            String key = name.next();
            if ("idrecord".equals(key) || "state".equals(key)) continue;
            String[] value = map.get(key);
            StringBuilder v = new StringBuilder();
            for (int i = 0, len = value.length; i < len; i++) {
                if (i != 0) v.append(",");
                v.append(value[i]);
            }
            RecordValue r = new RecordValue();
            r.setIdrecord(idrecord);
            r.setIdrecord_value(UUID.randomUUID().toString());
            r.setRecord_ele(key);
            r.setRecord_value(v.toString());
            res += emrRecordDao.insertRecordValue(r);
        }
        return res;
    }

    /**
     * 条件查询详情记录表
     *
     * @param recordValue 1
     * @return 1
     */
    public List<RecordValue> getRecordValue(RecordValue recordValue) {
        return emrRecordDao.getRecordValue(recordValue);
    }

    public int deleterecord(Record record) {
        RecordValue recordValue = new RecordValue();
        recordValue.setIdrecord(record.getIdrecord());
        int res = emrRecordDao.deleteRecordValue(recordValue);
        /*if(res>0)*/
        res = emrRecordDao.deleterecord(record);//有判断没填写删不了
        return res;
    }

    /**
     * 获取记录表中某一次记录的其中一些参数
     *
     * @param request 1
     * @return 1
     */
    public List<Map<String, Object>> getPartRecordValue(HttpServletRequest request) {
        String name = request.getParameter("names");//多个用英文逗号分隔，带belong后缀不用写出来
        if (name == null) return null;
        List<Map<String, Object>> res = null;
        Map<String, String> map;
        for (String s : name.split(",")) {
            map = new HashMap<>();
            map.put("namel", s + "[%");//需要获取值的name属性集合
            map.put("nameb", s + "belong");//需要获取值的name属性集合
            map.put("idrecord", request.getParameter("idrecord"));//评估记录表主键
            if (res == null) res = emrRecordDao.getPartRecordValue(map);
            else res.addAll(emrRecordDao.getPartRecordValue(map));
        }
        return res;
    }
}
