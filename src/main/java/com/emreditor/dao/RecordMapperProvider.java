package com.emreditor.dao;

import com.emreditor.beans.Record;
import org.apache.ibatis.jdbc.SQL;

public class RecordMapperProvider {
    public String insetrecord(Record record) {
        return new SQL() {
            {
                if (record.getIdpage() != null && record.getIdpage().length() > 0)
                    VALUES("idpage", "#{idpage}");
                if (record.getIdrecord() != null && record.getIdrecord().length() > 0)
                    VALUES("idrecord", "#{idrecord}");
                if (record.getPage_backimg() != null && record.getPage_backimg().length() > 0)
                    VALUES("page_backimg", "#{page_backimg}");
                if (record.getRecord_create() != null && record.getRecord_create().length() > 0)
                    VALUES("record_create", "#{record_create}");
                if (record.getRecord_title() != null && record.getRecord_title().length() > 0)
                    VALUES("record_title", "#{record_title}");
                if (record.getRecord_update() != null && record.getRecord_update().length() > 0)
                    VALUES("record_update", "#{record_update}");
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
            }
        }.toString();
    }
}
