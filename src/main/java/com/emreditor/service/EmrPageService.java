package com.emreditor.service;


import com.emreditor.beans.Page;
import com.emreditor.beans.Page_ele;
import com.emreditor.beans.Page_table;
import com.emreditor.beans.Page_table_ele;
import com.emreditor.dao.EmrPageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.emreditor.util.URIUtil.sendGet;

@Service
public class EmrPageService {
    private EmrPageDao emrPageDao;

    @Autowired
    public EmrPageService(EmrPageDao emrPageDao) {
        this.emrPageDao = emrPageDao;
    }

    /**
     * 条件查询
     *
     * @param page 1
     * @return 1
     */
    public List<Page> getDbType(Page page) {
        return emrPageDao.getDbType(page);
    }

    /**
     * 新增
     *
     * @param page 1
     * @return 1
     */
    public int addDbType(Page page) {
        return emrPageDao.addDbType(page);
    }

    /**
     * 更新
     *
     * @param page 1
     * @return 1
     */
    public int updDbType(Page page) {
        return emrPageDao.updDbType(page);
    }

    /**
     * 删除
     *
     * @return 1
     */
    public int delPageData(Page page) {
        Page_ele page_ele = new Page_ele();
        page_ele.setIdpage(page.getIdpage());
        List<Page_ele> list = emrPageDao.getPageEle(page_ele);
        //删除table表数据（需要修改为批处理的方式进行删除，不然删除效率太低）
        for (int i = 0, len = list.size(); i < len; i++) {
            Page_table page_table = new Page_table();
            page_table.setIdele(list.get(i).getIdele());
            List<Page_table> lis=emrPageDao.getPageTable(page_table);
            for(int j=0, le = lis.size();j<le;j++){
                Page_table_ele page_table_ele = new Page_table_ele();
                page_table_ele.setPage_table_id(lis.get(j).getIdtable());
                emrPageDao.delTableTdEle(page_table_ele);
            }
            emrPageDao.delPageTable(page_table);
        }
        //删除ele数据
        emrPageDao.delPageEle(page_ele);
        //删除主表数据
        return emrPageDao.delPageData(page);
    }

    /**
     * 条件查询页面元素项
     *
     * @param page_ele 1
     * @return 1
     */
    public List<Page_ele> getPageEle(Page_ele page_ele) throws Exception {
        List<Page_ele> list = emrPageDao.getPageEle(page_ele);
        if (page_ele.getIdele() == null)
            for (int i = 0, len = list.size(); i < len; i++) {
                Page_ele p = list.get(i);
                String ele_conn = p.getEle_conn();
                if (ele_conn != null && ele_conn.length() > 0) {
                    list.get(i).setEle_conn(sendGet(ele_conn, null));
                }
                //判断是不是表格，对表格进行对应的操作，主要查询表格tdd中表单元素的值
                if (!"10".equals(p.getEle_type())) continue;
                //查询表格操作
                Page_table pt = new Page_table();
                pt.setIdele(p.getIdele());
                List<Page_table> lis = emrPageDao.getPageTable(pt);
                for (int j = 0, le = lis.size(); j < le; j++) {
                    Page_table_ele page_table_ele = new Page_table_ele();
                    page_table_ele.setPage_table_id(lis.get(j).getIdtable());
                    //查询表格中的每一项元素，并进行对应的操作
                    List<Page_table_ele> li = emrPageDao.getTableEle(page_table_ele);
                    for (int k = 0, l = li.size(); k < l; k++) {
                        Page_table_ele tableEle = li.get(k);
                        String conn = tableEle.getEle_conn();
                        if (conn == null || conn.length() == 0) continue;
                        li.get(k).setEle_conn(sendGet(conn, null));
                    }
                    lis.get(j).setTableEle(li);
                }
                list.get(i).setPage_tables(lis);
            }
        return list;
    }

    /**
     * 新增页面元素项
     *
     * @param page_ele 1
     * @return 1
     */
    public int addPageEle(Page_ele page_ele) {
        page_ele.setShow_seq(emrPageDao.getPageEleCount(page_ele));
        return emrPageDao.addPageEle(page_ele);
    }

    /**
     * 修改页面元素项
     *
     * @param page_ele 1
     * @return 1
     */
    public int updPageEle(Page_ele page_ele) {
        return emrPageDao.updPageEle(page_ele);
    }

    /**
     * 删除页面元素项
     *
     * @param page_ele 1
     * @return 1
     */
    public int delPageEle(Page_ele page_ele) {
        emrPageDao.updPageEleSwq(page_ele);
        return emrPageDao.delPageEle(page_ele);
    }

    /**
     * 更新元素显示顺序
     *
     * @param map 1
     * @return 1
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
     * @return 1
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

    private Page_table initPage_table(String idele, String trTd, int seq, int row, int col) {
        Page_table t = new Page_table();
        t.setIdtable(UUID.randomUUID().toString());
        t.setIdele(idele);
        t.setLabel_type(trTd);
        t.setShow_seq(seq);
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
     * @param p 1
     * @return 1
     */
    public int delpageTable(Page_table p) {
        Page_table pt = new Page_table();
        pt.setIdele(p.getIdele());
        List<Page_table> li = emrPageDao.getPageTable(pt);
        for (int k = 0, l = li.size(); k < l; k++) {
            Page_table_ele page_table_ele = new Page_table_ele();
            page_table_ele.setPage_table_id(li.get(k).getIdtable());
            emrPageDao.delTableTdEle(page_table_ele);
        }
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
        if (res > 0) {
            map.put("col", request.getParameter("col"));
            map.put("row", request.getParameter("row"));
            res = emrPageDao.tdMergeUpdTd(map);
        }
        return res;
    }

    /**
     * 拆分单元格
     *
     * @param request 1
     */
    public int tdBreak(HttpServletRequest request) {
        int row = Integer.parseInt(request.getParameter("row"));//开始行
        int col = Integer.parseInt(request.getParameter("col"));//开始列
        String idele = request.getParameter("idele");//当前表格在元素表中的元素id
        String idtable = request.getParameter("idtable");//当前单元格记录id
        int rowspan = Integer.parseInt(request.getParameter("rowspan"));//合并行数
        int colspan = Integer.parseInt(request.getParameter("colspan"));//合并列数
        for (int i = 0; i < rowspan; i++) {
            for (int j = 0; j < colspan; j++) {
                emrPageDao.createTableRowCol(initPage_table(idele, "td", -1, row + i, col + j));
            }
        }
        Page_table page_table = new Page_table();
        page_table.setIdtable(idtable);
        return emrPageDao.delPageTable(page_table);
    }

    /**
     * 插入行或列
     *
     * @param request 1
     * @param type    col 插入列，row 插入行
     * @return 1
     */
    public int insetRowCol(HttpServletRequest request, String type) {
        int row = Integer.parseInt(request.getParameter("row"));//当前行
        int col = Integer.parseInt(request.getParameter("col"));//总共的列数-1
        String idele = request.getParameter("idele");
        Page_table page_table = new Page_table();
        page_table.setIdele(idele);
        int res = 0;
        switch (type) {
            case "col":
                page_table.setTdcol(col);
                res = emrPageDao.insetRowCol(page_table);
                if (res > 0)
                    for (int i = 0; i <= row; i++) {
                        emrPageDao.createTableRowCol(initPage_table(idele, "td", -1, i, col));
                        ++res;
                    }
                break;
            case "row":
                page_table.setTdrow(row);
                res = emrPageDao.insetRowCol(page_table);
                if (res > 0)
                    for (int i = 0; i <= col; i++) {
                        emrPageDao.createTableRowCol(initPage_table(idele, "td", -1, row, i));
                        ++res;
                    }
                break;
        }
        return res;
    }

    /**
     * 删除行或列之后跟新行列信息
     *
     * @param page_table 1
     * @return 1
     */
    public int delRowCol(Page_table page_table) {
        int res = emrPageDao.delRowCol(page_table);
        if (res > 0) res = emrPageDao.delUpdRowCol(page_table) == 0 ? res : 1;
        return res;
    }

    /**
     * 更新单元格信息
     *
     * @param page_table 1
     * @return 1
     */
    public int updPageTable(Page_table page_table) {
        return emrPageDao.updPageTable(page_table);
    }

    /**
     * 条件查询单元格信息
     *
     * @param page_table 1
     * @return 1
     */
    public List<Page_table> getTableCol(Page_table page_table) {
        return emrPageDao.getTableCol(page_table);
    }

    /**
     * 删除表格td中的表单元素
     *
     * @param page_table_ele 1
     * @return 1
     */
    public int delTableTdEle(Page_table_ele page_table_ele) {
        return emrPageDao.delTableTdEle(page_table_ele);
    }

    public int insetTableEle(Page_table_ele page_table_ele) {
        page_table_ele.setIdpage_table_ele(UUID.randomUUID().toString());
        return emrPageDao.insetTableEle(page_table_ele);
    }
}
