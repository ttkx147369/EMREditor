package com.emreditor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Util {
    /**
     * 更具map中的key和value生成inser相关语句
     * @param param
     * @return Object[]数组 0：insert字段列表 1：inser值列表？，？的形式 2：参数值列表
     */
    public static Object[] insertsql(Map<String, String[]> param){
        StringBuffer sql = new StringBuffer();//sql语句中的
        StringBuffer p=new StringBuffer();
        List<Object> obj=new ArrayList<>();
        for (String col : param.keySet()) {
            if(param.get(col)==null) continue;
            String v=map(param, col);
            if(v.length()==0) continue;
            if(obj.size()!=0) {
                sql.append(",");
                p.append(",");
            }else {
                //p.append("'");
            }
            sql.append(col);
            p.append("?");
            obj.add(v);
        }
        return new Object[]{sql, p, obj};
    }

    /**
     * 生成select中的where查询条件部分（仅支持字符类型字段）
     * @param param
     * @return
     */
    public static Object[] selWhereSql(Map<String, String[]> param){
        StringBuffer sql = new StringBuffer();
        List<Object> l=new ArrayList();
        for(String col : param.keySet()){
            if(param.get(col)==null) continue;
            String v=map(param, col);
            if(v.length()==0) continue;
            if(sql.length()==0) sql.append(" where ").append(col).append(" like '%||?||%' ");
            else sql.append(" and ").append(col).append("=").append(" like '%||?||%' ");
            l.add(v);
        }
        return new Object[]{sql,l};
    }

    public static String map(Map<String, String[]> param, String col){
        StringBuffer v=new StringBuffer();
        for(int i=0,len=param.get(col).length;i<len;i++){
            if(i!=0)v.append(",");
            v.append(param.get(col)[i]);
        }
        return v.toString();
    }


    public static void main(String[] args){
        Map<String, String[]> map=new HashMap<>();
        map.put("a", new String[]{"1"});
        map.put("v", new String[]{"3"});
        map.put("b", new String[]{});
        insertsql(map);
    }
}
