package com.emreditor.service;


import com.emreditor.beans.Page;
import com.emreditor.beans.Page_ele;
import com.emreditor.beans.Page_table;
import com.emreditor.dao.EmrPageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.emreditor.util.URIUtil.sendGet;

@Service
public class EmrPageService {
    @Autowired
    public EmrPageDao emrPageDao;

    /**
     * 条件查询
     *
     * @param page
     * @return
     */
    public List<Page> getDbType(Page page) {
        return emrPageDao.getDbType(page);
    }

    /**
     * 新增
     *
     * @param page
     * @return
     */
    public int addDbType(Page page) {
        return emrPageDao.addDbType(page);
    }

    /**
     * 更新
     *
     * @param page
     * @return
     */
    public int updDbType(Page page) {
        return emrPageDao.updDbType(page);
    }

    /**
     * 删除
     *
     * @return
     */
    public int delPageData(Page page) {
        return emrPageDao.delPageData(page);
    }

    /**
     * 条件查询页面元素项
     *
     * @param page_ele
     * @return
     */
    public List<Page_ele> getPageEle(Page_ele page_ele) throws Exception {
        List<Page_ele> list = emrPageDao.getPageEle(page_ele);
        if (page_ele.getIdele() == null)
            for (int i = 0, len = list.size(); i < len; i++) {
                String ele_conn = list.get(i).getEle_conn();
                if (ele_conn == null || ele_conn.length() == 0) continue;
                list.get(i).setEle_conn(sendGet(ele_conn, null));
            }
        for (int i = 0, len = list.size(); i < len; i++) {
            Page_ele p = list.get(i);
            if (!"10".equals(p.getEle_type())) continue;
            //查询表格
            Page_table pt = new Page_table();
            pt.setIdele(p.getIdele());
            List<Page_table> lis = emrPageDao.getPageTable(pt);
            for(int j=0,le=lis.size();j<le;j++){
                String ele_conn=lis.get(j).getEle_conn();
                if(ele_conn == null || ele_conn.length() == 0) continue;
                lis.get(j).setEle_conn(sendGet(ele_conn,null));
            }
            list.get(i).setPage_tables(lis);
        }
        return list;
    }

    /**
     * 新增页面元素项
     *
     * @param page_ele
     * @return
     */
    public int addPageEle(Page_ele page_ele) {
        page_ele.setShow_seq(emrPageDao.getPageEleCount(page_ele));
        return emrPageDao.addPageEle(page_ele);
    }

    /**
     * 修改页面元素项
     *
     * @param page_ele
     * @return
     */
    public int updPageEle(Page_ele page_ele) {
        return emrPageDao.updPageEle(page_ele);
    }

    /**
     * 删除页面元素项
     *
     * @param page_ele
     * @return
     */
    public int delPageEle(Page_ele page_ele) {
        emrPageDao.updPageEleSwq(page_ele);
        return emrPageDao.delPageEle(page_ele);
    }

    /**
     * 更新元素显示顺序
     *
     * @param map
     * @return
     */
    public int updseq(Map<String, String> map) {
        emrPageDao.updseq(map);
        return emrPageDao.updseq1(map);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////         表格操作部分        //////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 创建表格的行和列
     *
     * @param tablecol 表格列数
     * @param tablerow 表格行数
     * @return
     */
    public int createTableRowCol(String tablecol, String tablerow, String idele) {
        List<Page_table> list = new ArrayList<>();
        int row = Integer.parseInt(tablerow);
        int col = Integer.parseInt(tablecol);
        int seq = 0;
        for (int i = 0; i < row; i++) {
            //emrPageDao.createTableRowCol(initPage_table(idele, "tr", seq++, -1, -1));
            for (int j = 0; j < col; j++) {
                emrPageDao.createTableRowCol(initPage_table(idele, "td", seq++, i, j));
            }
        }
        return seq;
    }

    private Page_table initPage_table(String idele, String trtd, int seq, int row, int col) {
        Page_table t = new Page_table();
        t.setIdtable(UUID.randomUUID().toString());
        t.setIdele(idele);
        t.setLabel_type(trtd);
        t.setShow_seq(seq++);
        t.setTdrow(row);
        t.setTdcol(col);
        t.setShowcontent("&nbsp;");
        t.setRowspan(1);
        t.setColspan(1);
        t.setCssstyle("");
        t.setTextcss("");
        return t;
    }

    /**
     * 删除表格元素的单元格
     *
     * @param p
     * @return 1
     */
    public int delpageTable(Page_table p) {
        return emrPageDao.delPageTable(p);
    }

    /**
     * table表格单元格合并
     *
     * @param request 请求对象，包含起止位置
     * @return 1
     */
    public int tdMerge(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("scol", request.getParameter("scol"));
        map.put("srow", request.getParameter("srow"));
        map.put("idele", request.getParameter("idele"));
        map.put("ecol", request.getParameter("ecol"));
        map.put("erow", request.getParameter("erow"));
        int res = emrPageDao.tdMergeDelTd(map);
        if(res>0){
            map.put("col", request.getParameter("col"));
            map.put("row", request.getParameter("row"));
            res=emrPageDao.tdMergeUpdTd(map);
        }
        return res;
    }

    /**
     * 拆分单元格
     * @param request 1
     */
    public int tdBreak(HttpServletRequest request) {
        int row = Integer.parseInt(request.getParameter("row"));//开始行
        int col = Integer.parseInt(request.getParameter("col"));//开始列
        String idele = request.getParameter("idele");//当前表格在元素表中的元素id
        String idtable = request.getParameter("idtable");//当前单元格记录id
        int rowspan = Integer.parseInt(request.getParameter("rowspan"));//合并行数
        int colspan = Integer.parseInt(request.getParameter("colspan"));//合并列数
        for(int i=0;i<rowspan;i++){
            for(int j=0;j<colspan;j++){
                emrPageDao.createTableRowCol(initPage_table(idele,"td", -1,row+i,col+j));
            }
        }
        Page_table page_table=new Page_table();
        page_table.setIdtable(idtable);
        return emrPageDao.delPageTable(page_table);
    }

    /**
     * 插入行或列
     * @param request 1
     * @param type col 插入列，row 插入行
     * @return 1
     */
    public int insetRowCol(HttpServletRequest request, String type) {
        int row = Integer.parseInt(request.getParameter("row"));//当前行
        int col = Integer.parseInt(request.getParameter("col"));//总共的列数-1
        String idele=request.getParameter("idele");
        Page_table page_table = new Page_table();
        page_table.setIdele(idele);
        int res=0;
        switch (type){
            case "col":
                page_table.setTdcol(col);
                res=emrPageDao.insetRowCol(page_table);
                if(res>0)
                    for (int i=0;i<=row;i++){
                        emrPageDao.createTableRowCol(initPage_table(idele,"td", -1, i, col));
                        ++res;
                    }
                break;
            case "row":
                page_table.setTdrow(row);
                res=emrPageDao.insetRowCol(page_table);
                if(res>0)
                    for (int i=0;i<=col;i++){
                        emrPageDao.createTableRowCol(initPage_table(idele,"td", -1, row, i));
                        ++res;
                    }
                break;
        }
        return res;
    }

    /**
     * 删除行或列之后跟新行列信息
     * @param page_table 1
     * @return 1
     */
    public int delRowCol(Page_table page_table) {
        int res=emrPageDao.delRowCol(page_table);
        if(res>0) res=emrPageDao.delUpdRowCol(page_table)==0?res:1;
        return res;
    }

    /**
     * 更新单元格信息
     * @param page_table 1
     * @return 1
     */
    public int updPageTable(Page_table page_table) {
        return emrPageDao.updPageTable(page_table);
    }

    /**
     * 条件查询单元格信息
     * @param page_table 1
     * @return 1
     */
    public List<Page_table> getTableCol(Page_table page_table) {
        return emrPageDao.getTableCol(page_table);
    }
}
