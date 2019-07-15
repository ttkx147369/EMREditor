package com.emreditor.service;

import com.emreditor.beans.Record;
import com.emreditor.beans.RecordResult;
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
        String idpage=request.getParameter("idpage");
        String idrecord = request.getParameter("idrecord");
        String state = request.getParameter("state");
        //修改记录状态
        Record record = new Record();
        record.setIdrecord(idrecord);
        record.setRecord_state(state);
        emrRecordDao.updRecord(record);
        emrRecordDao.deleteRecordResult(idrecord);//删除之前的评估结果
        Map<String, String[]> map = request.getParameterMap();
        Set<String> set=new TreeSet<>();//用TreeSet包装一下，让Set有序
        set.addAll(map.keySet());
        Iterator<String> name = set.iterator();

        StringBuilder sql=new StringBuilder("insert into `").append(idpage).append("`(`");
        StringBuilder updsql=new StringBuilder("update `").append(idpage).append("` set `");
        StringBuilder val=new StringBuilder(") values('");
        String k="";
        while (name.hasNext()) {
            String key = name.next();
            if ("idrecord".equals(key) || "state".equals(key) || "idpage".equals(key)) continue;
            String[] value = map.get(key);
            key=key.split("\\[")[0];
            StringBuilder v = new StringBuilder();
            for (int i = 0, len = value.length; i < len; i++) {
                if (i != 0) v.append(",");
                //v.append(value[i].replaceAll(",", "，").replaceAll("\\[", "【").replaceAll("]", "】"));
                v.append(value[i]);
            }
            if(k.length()>0){
                if(!k.equals(key)){
                    val.append("','").append(v);
                    sql.append("`,`").append(key);
                    updsql.append("',`").append(key).append("`='").append(v);
                    k=key;
                }else{
                    val.append(",").append(v);
                    updsql.append(",").append(v);
                }
            }else {
                val.append(v);
                sql.append(key);
                updsql.append(key).append("`='").append(v);
                k=key;
            }
        }
        updsql=new StringBuilder(updsql).append("' where id='").append(idrecord).append("'");
        res=emrRecordDao.updateRecordValue(updsql);
        if(res>0) return res;

        sql.append("`,`id`").append(val).append("','").append(idrecord).append("')");
        res=emrRecordDao.insertRecordValue(sql);
        return res;
    }

    /**
     * 条件查询详情记录表
     *
     * @param request 1
     * @return 1
     */
    public List<Map<String, Object>> getRecordValue(HttpServletRequest request) {
        String idrecord=request.getParameter("idrecord");
        String idpage=request.getParameter("idpage");
        return emrRecordDao.getRecordValue(new StringBuilder("select * from `").append(idpage).append("` where id='").append(idrecord).append("'"));
    }

    public int deleterecord(Record record) {
        StringBuilder sb=new StringBuilder("delete from `").append(record.getIdpage()).append("` where id='").append(record.getIdrecord()).append("'");
        int res = emrRecordDao.deleteRecordValue(sb);
        res = emrRecordDao.deleteRecordResult(record.getIdrecord());
        res = emrRecordDao.deleterecord(record);//有判断没填写删不了
        return res;
    }

    /**
     * 获取记录表中某一次记录的其中一些参数
     *
     * @param request 1
     * @return 1
     */
    /*public List<Map<String, Object>> getPartRecordValue(HttpServletRequest request) {
        String name = request.getParameter("names");//多个用英文逗号分隔，带belong后缀不用写出来
        String idrecord = request.getParameter("idrecord");
        if (name == null) return null;
        List<Map<String, Object>> res = null;
        Map<String, String> map;
        for (String s : name.split(",")) {
            map = new HashMap<>();
            map.put("namel", s + "[%");//需要获取值的name属性集合
            map.put("nameb", s + "belong");//需要获取值的name属性集合
            map.put("idrecord", idrecord);//评估记录表主键
            if (res == null) res = emrRecordDao.getPartRecordValue(map);
            else res.addAll(emrRecordDao.getPartRecordValue(map));
        }
        return res;
    }*/

    /**
     * 保存评估结果到评估表中
     * @param recordResult 1
     * @return 1
     */
    public int saveRecordValue(RecordResult recordResult) {
        recordResult.setIdrecord_result(UUID.randomUUID().toString());
        return emrRecordDao.saveRecordValue(recordResult);
    }
    /**
     * 查询评估结果
     * @param recordResult 1
     * @return 1
     */
    public List<RecordResult> getRecordResult(RecordResult recordResult) {
        return emrRecordDao.getRecordResult(recordResult);
    }
}
