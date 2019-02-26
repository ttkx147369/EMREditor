package com.emreditor.controller;

import com.emreditor.beans.Page;
import com.emreditor.beans.Page_ele;
import com.emreditor.beans.Page_table;
import com.emreditor.beans.Page_table_ele;
import com.emreditor.service.EmrPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 电子病历设计数据保存
 */
@RestController
@RequestMapping("dpage")
public class EmrDPageController {
    private final EmrPageService emrPageService;

    @Autowired
    public EmrDPageController(EmrPageService emrPageService) {
        this.emrPageService = emrPageService;
    }

    /**
     * 向病历主表添加记录(记录更新暂时不可用)
     */
    @RequestMapping("addpAddData")
    public String addpAddData(Page page) {
    	String id=page.getIdpage();
        if (id == null || id.length() == 0) {
        	id=UUID.randomUUID().toString();
            page.setIdpage(id);
            page.setPage_create(new Date());
            emrPageService.addDbType(page);
            return id;
        } else {
            page.setPage_update(new Date());
            int res = emrPageService.updDbType(page);
            if(res == 0) emrPageService.addDbType(page);
            return id;
        }
    }

    /**
     * 查询病历主表的数据
     */
    @RequestMapping("getPageData")
    public List<Page> getPageData(Page page) {
        return emrPageService.getDbType(page);
    }

    /**
     * 删除数据
     */
    @RequestMapping("delPageData")
    public int delPageData(Page page) {
        //String idpage = request.getParameter("idpage");
        return emrPageService.delPageData(page);
    }

    /**
     * 条件查询页面元素项
     */
    @RequestMapping("getPageEle")
    public List<Page_ele> getPageEle(Page_ele page_ele) throws Exception {
        return emrPageService.getPageEle(page_ele);
    }

    /**
     * 新增页面元素项
     */
    @RequestMapping("addPageEle")
    public int addPageEle(HttpServletRequest request, Page_ele page_ele) {
        System.out.println(page_ele.getIdele());
        if (page_ele.getIdele() == null || page_ele.getIdele().length() == 0) {
            String id = UUID.randomUUID().toString();
            page_ele.setIdele(id);
            int res=emrPageService.addPageEle(page_ele);

            String tablerow = request.getParameter("tablerow");
            String tablecol = request.getParameter("tablecol");
            if (tablecol != null && tablecol.length() != 0 && tablerow != null && tablerow.length() != 0)
                emrPageService.createTableRowCol(tablecol, tablerow, id);

            return res;
        } else {
            return emrPageService.updPageEle(page_ele);
        }
    }

    /**
     * 删除页面元素项
     */
    @RequestMapping("delPageEle")
    public int delPageEle(HttpServletRequest request, Page_ele page_ele) {
        if("table".equals(request.getParameter("type"))){
            Page_table p=new Page_table();
            p.setIdele(page_ele.getIdele());
            int res=emrPageService.delpageTable(p);
        }
        return emrPageService.delPageEle(page_ele);
    }

    /**
     * 更新元素显示顺序
     */
    @RequestMapping("updseq")
    public int updseq(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("idpage", request.getParameter("idpage"));
        map.put("oldseq", request.getParameter("oldseq"));
        map.put("newseq", request.getParameter("newseq"));
        map.put("oldid", request.getParameter("oldid"));
        map.put("newid", request.getParameter("newid"));
        return emrPageService.updseq(map);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////         表格操作部分        //////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 单元格合并
     * @return 1
     */
    @RequestMapping("tdMerge")
    public int tdMerge(HttpServletRequest request){
        return emrPageService.tdMerge(request);
    }

    /**
     * 拆分合并的单元格
     * @param request 1
     * @return 1
     */
    @RequestMapping("tdBreak")
    public int tdBreak(HttpServletRequest request){
        return emrPageService.tdBreak(request);
    }

    /**
     * 向表格中插入一行
     * @param request 1
     * @return q
     */
    @RequestMapping("insetrow")
    public int insetrow(HttpServletRequest request){
        return emrPageService.insetRowCol(request, "row");
    }

    /**
     * 擦汗如一列
     * @param request 1
     * @return 1
     */
    @RequestMapping("insetcol")
    public int insetcol(HttpServletRequest request){
        return emrPageService.insetRowCol(request, "col");
    }

    /**
     * 删除行或列
     * @param page_table 1
     * @return 1
     */
    @RequestMapping("delRowCol")
    public int delRowCol(Page_table page_table){
        return emrPageService.delRowCol(page_table);
    }

    /**
     * 更新单元格信息
     * @param page_table 1
     * @return 1
     */
    @RequestMapping("updPageTable")
    public  int updPageTable(Page_table page_table){
        return emrPageService.updPageTable(page_table);
    }

    /**
     * 条件查询单元格信息
     * @param page_table 1
     * @return 1
     */
    @RequestMapping("getTableCol")
    public List<Page_table> getTableCol(Page_table page_table){
        return emrPageService.getTableCol(page_table);
    }

    /**
     * 删除表格td中的表单元素
     * @param page_table_ele 1
     * @return 1
     */
    @RequestMapping("delTableTdEle")
    public int delTableTdEle(Page_table_ele page_table_ele){
        return emrPageService.delTableTdEle(page_table_ele);
    }
    @RequestMapping("insetTableEle")
    public int insetTableEle(Page_table_ele page_table_ele){
        return emrPageService.insetTableEle(page_table_ele);
    }
}
