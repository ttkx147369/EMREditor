package com.emreditor.beans;

import java.sql.Timestamp;

public class RecordResult {
    private String idrecord_result;
    private String idpage;
    private String idrecord;
    private String record_result;
    private Timestamp opt_time;
    private String bak1;
    private String bak2;
    private String bak3;

    public String getIdrecord_result() {
        return idrecord_result;
    }

    public void setIdrecord_result(String idrecord_result) {
        this.idrecord_result = idrecord_result;
    }

    public String getIdpage() {
        return idpage;
    }

    public void setIdpage(String idpage) {
        this.idpage = idpage;
    }

    public String getIdrecord() {
        return idrecord;
    }

    public void setIdrecord(String idrecord) {
        this.idrecord = idrecord;
    }

    public String getRecord_result() {
        return record_result;
    }

    public void setRecord_result(String record_result) {
        this.record_result = record_result;
    }

    public Timestamp getOpt_time() {
        return opt_time;
    }

    public void setOpt_time(Timestamp opt_time) {
        this.opt_time = opt_time;
    }

    public String getBak1() {
        return bak1;
    }

    public void setBak1(String bak1) {
        this.bak1 = bak1;
    }

    public String getBak2() {
        return bak2;
    }

    public void setBak2(String bak2) {
        this.bak2 = bak2;
    }

    public String getBak3() {
        return bak3;
    }

    public void setBak3(String bak3) {
        this.bak3 = bak3;
    }
}
