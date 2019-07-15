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
     * 条件查询(如果查出数据为空则新增一条记录)
     *
     * @param page 1
     * @return 1
     */
    public List<Page> getDbType(Page page) {
        List<Page> pageList=emrPageDao.getDbType(page);
        if(pageList!=null&&!pageList.isEmpty()) return pageList;
        page.setPage_create(new Date());
        //page.setPage_type("2");
        if(addDbType(page)>0){
            //如果是复杂评估页面，需要初始化一个表格
            if("4".equals(page.getPage_type())){
                String idpage=page.getIdpage();
                Page_ele page_ele=new Page_ele();
                page_ele.setIdele(idpage);
                page_ele.setIdpage(idpage);
                page_ele.setEle_type("10");
                page_ele.setOccupy_col("1");
                page_ele.setOccupy_row("1");
                page_ele.setShow_seq(0);
                page_ele.setTextcss("1");
                int res=addPageEle(page_ele);
                if(res>0){
                    createTableRowCol("6", "10", idpage);
                }
            }

            page.setPage_type(null);
            page.setPage_create(null);
            pageList=emrPageDao.getDbType(page);
        }
        return pageList;
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
        //删除当前模板对应的数据库表
        emrPageDao.dropTable(page.getIdpage());
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
                //判断是不是表格，对表格进行对应的操作，主要查询表格td中表单元素的值
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

    /**
     * 在模板编辑完成之后点击保存按钮
     * 将新建一个以模板id为名称的表
     * 以模板中的所有表单元素name为列名的表
     * 用于保存数据
     * @param page_ele 1
     * @return 1
     */
    public int savePage(Page_ele page_ele) {
        int res = 0;
        List<String> ele_id=new ArrayList<>();
        List<Page_ele> list = emrPageDao.getPageEle(page_ele);
        if (page_ele.getIdele() == null)
            for (int i = 0, len = list.size(); i < len; i++) {
                Page_ele p = list.get(i);
                String ele_type=p.getEle_type();
                if("3".equals(ele_type)||"4".equals(ele_type)||"5".equals(ele_type)||"6".equals(ele_type)||"7".equals(ele_type)){
                    ele_id.add(p.getEle_id());
                }
                if (!"10".equals(p.getEle_type())) continue;
                //查询表格操作
                Page_table pt = new Page_table();
                pt.setIdele(p.getIdele());
                List<Page_table_ele> page_table_eles=emrPageDao.getTableEleByPageid(p);
                for(int j = 0, le=page_table_eles.size(); j<le;j++){
                    Page_table_ele tableEle = page_table_eles.get(j);
                    String tele_type=tableEle.getEle_type();
                    if("3".equals(tele_type)||"4".equals(tele_type)||"5".equals(tele_type)||"6".equals(tele_type)||"7".equals(tele_type)){
                        if(tableEle.getEle_id()==null) continue;
                        ele_id.add(tableEle.getEle_id());
                    }
                }
            }
        if(emrPageDao.getEmrTable(page_ele)>0){//表存在，只需要添加不存在的字段
            List<String> column=emrPageDao.getEmrTableColumn(page_ele);
            for(String str:column){
                ele_id.remove(str);
            }
            if(!ele_id.isEmpty())
                res=emrPageDao.alterTable(page_ele,ele_id);
        }else{//表不存在需要重新创建表
            res=emrPageDao.createTable(page_ele,ele_id);
        }
        return res;
    }

    /**
     * 保存比纳基模板的时候增加page_type==4的评估结果保存列和条件
     * @param col 1  ,
     * @param row 1  , ~
     * @param idpage 1
     * @return 1
     */
    public int saveCondition(String col, String row, String idpage) {
        Map<String, String> map=new HashMap<>();
        col=col.length()==0?"-1":col;
        map.put("idpage", idpage);
        map.put("cols", col);
        int res=emrPageDao.setVlaueColumn(map);
        String[] rows=row.split(",");

        //List<Page_table> page_tables=new ArrayList<>();
        for(int i=0,len=rows.length;i<len;i++){
            Page_table page_table=new Page_table();
            String[] rc=rows[i].split("~");
            if(rc[0].length()==0) continue;
            page_table.setTdrow(Integer.parseInt(rc[0]));
            page_table.setCondition(rc[1]);
            page_table.setIdele(idpage);
            res=emrPageDao.setConditionRow(page_table);
            //page_tables.add(page_table);
        }
        return res;
    }
    /**
     * 根据idpage查询整个评估表中的所有表单元素name值
     * @param request 1
     * @return 1
     */
    public List<Map> getFormEleName(HttpServletRequest request) {
        Page page=new Page();
        page.setIdpage(request.getParameter("idpage"));
        return emrPageDao.getFormEleName(page);
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
        map.put("formid", request.getParameter("formid"));
        map.put("showcontent", request.getParameter("showcontent"));
        int res = emrPageDao.tdMergeTd(map);

        if (res > 0) {
            map.put("col", request.getParameter("col"));
            map.put("row", request.getParameter("row"));
            res = emrPageDao.tdMergeUpdTd(map);

            map.put("downtdid", request.getParameter("downtdid"));
            emrPageDao.delTableTdEleByRowCol(map);
            emrPageDao.updTableTdEleByRowCol(map);
        }
        return res;
    }

    /**
     * 拆分单元格
     *
     * @param request 1
     */
    public int tdBreak(HttpServletRequest request) {
        Map<String, String> map=new HashMap<>();
        map.put("row", request.getParameter("row"));//开始行
        map.put("col", request.getParameter("col"));//开始列
        map.put("idele", request.getParameter("idele"));//当前表格在元素表中的元素id
        //map.put("", "idtable");//当前单元格记录id
        map.put("rowspan", request.getParameter("rowspan"));//合并行数
        map.put("colspan", request.getParameter("colspan"));//合并列数
        int res = emrPageDao.tdBreakTd(map);
        if(res==0) return res;
        Page_table page_table=new Page_table();
        page_table.setIdtable(request.getParameter("idtable"));
        page_table.setShowcontent(request.getParameter("showcontent"));
        if(page_table.getShowcontent()!=null&&page_table.getShowcontent().length()>0)
            emrPageDao.updPageTable(page_table);
        return res;
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
        int res=0;
        if(page_table.getColspan()==null||page_table.getRowspan()==null){
            res=emrPageDao.updPageTable(page_table);
        }else if(page_table.getColspan()==1&&page_table.getRowspan()==1)
            res=emrPageDao.updPageTable(page_table);
        else {
            res=emrPageDao.updPageTableTds(page_table);
        }
        return res;
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
    public int delTableTdEle(Page_table page_table, Page_table_ele page_table_ele) {
        int res=emrPageDao.delTableTdEle(page_table_ele);
        if(res>0&&page_table.getColspan()>1||page_table.getRowspan()>1)
            emrPageDao.updPageTableTdFormid(page_table);
        return res;
    }

    /**
     * 插入td中的表单元素
    * @param page_table 1
     * @param page_table_ele 1
     * @return 1
     */
    public int insetTableEle(Page_table page_table, Page_table_ele page_table_ele) {
        page_table_ele.setIdpage_table_ele(UUID.randomUUID().toString());
        int res=emrPageDao.insetTableEle(page_table_ele);
        //if(res>0&&page_table.getColspan()>1||page_table.getRowspan()>1)
        if(res>0)
            emrPageDao.updPageTableTdFormid(page_table);
        return res;
    }
}
