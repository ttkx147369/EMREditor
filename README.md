# EMREditor
#idea项目，使用spring boot开发，里面使用spring MVC,spring,mybatis框架
#数据库采用mysql数据库
#在进行表格操作是仍然还有很多的一些bug，在有合并单元格的地方进行删除行列操作是会出现单元格没对齐的情况，只需要将合并的单元格完全拆分即可解决
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.sql.Connection;

/**
 * 将数据库表导出为execl文件
 */
public class TableExeclUtil {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static String tables = "select table_name,table_comment from information_schema.tables where table_schema = ?";

	private static String tableColumn = "select column_name,column_default,case is_nullable when 'NO' then '可为空' else '必须' end IS_NULLABLE,column_type,column_comment "
			+ "from information_schema.columns where table_schema = ? and table_name = ?";

	public static void main(String[] args) throws Exception {
		List<String> p = new ArrayList<>();
		p.add("cimb_batch");
		List<Map<String, String>> res = execSql(tables, p);
		// 创建Excel文件对象
		XSSFWorkbook wb = new XSSFWorkbook();
		createExcelTop(wb, "目录", new String[] { "序号", "表名", "表说明" }, new String[] {"TABLE_NAME", "TABLE_COMMENT"}, res);
		for(int i=0;i<res.size();i++) {
			Map<String, String> onetable = res.get(i);
			p = new ArrayList<>();
			p.add("cimb_batch");
			p.add(onetable.get("TABLE_NAME"));
			List<Map<String, String>> onetablecolumn = execSql(tableColumn, p);
			// for(int j=0;j<onetablecolumn.size();j++) {
				createExcelTop(wb, onetable.get("TABLE_COMMENT").isEmpty() ? onetable.get("TABLE_NAME") : onetable.get("TABLE_COMMENT"),
						new String[] { "序号", "列明名", "默认值", "是否必须", "数据类型", "列说明" },
						new String[] {"COLUMN_NAME", "COLUMN_DEFAULT", "IS_NULLABLE", "COLUMN_TYPE", "COLUMN_COMMENT"},
						onetablecolumn);
			// }
		}
		// 输出Excel文件
		FileOutputStream output = new FileOutputStream("文件路径");
		wb.write(output);
		output.flush();
		output.close();
	}

	/**
	 * 执行sql
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	private static List<Map<String, String>> execSql(String sql, List<String> params) {
		List<Map<String, String>> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = DriverManager.getConnection("连接地址", "用户名", "密码");
			ps = conn.prepareStatement(sql);
			for (int i = 0; i < params.size(); i++) {
				ps.setString(i+1, params.get(i));
			}
			rs = ps.executeQuery();
			ResultSetMetaData md = rs.getMetaData(); // 获得结果集结构信息,元数据
			int columnCount = md.getColumnCount(); // 获得列数
			while (rs.next()) {
				Map<String, String> rowData = new HashMap<>();
				for (int i = 1; i <= columnCount; i++) {
					rowData.put(md.getColumnName(i), rs.getString(i));
				}
				list.add(rowData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * 创建excel的表头,设置字体及字体颜色，设置单元格填充色
	 * 
	 * @param hssfWorkbook
	 * @param tableHead
	 * @param data
	 * @throws Exception
	 */
	public static void createExcelTop(XSSFWorkbook xf, String sheetName, String[] tableHead, String[] datacolumn, List<Map<String, String>> data) throws Exception {
		// 创建sheet对象
		Sheet sheet = xf.createSheet(sheetName);
		// 创建row
		Row row = sheet.createRow(1);

		//关键点 IndexedColors.AQUA.getIndex() 对应颜色
		CellStyle style = xf.createCellStyle();
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		// 创建cell
		for(int i=0;i<tableHead.length;i++) {
			Cell cell = row.createCell(i+1);
			cell.setCellStyle(style);
			cell.setCellValue(tableHead[i]);
		}

		for (int i = 1; i <= data.size(); i++) {
			Row row2 = sheet.createRow(i+1);
			row2.createCell(1).setCellValue("" + i);
			Map<String, String> onedata = data.get(i-1);
			for(int j=0;j<datacolumn.length;j++) {
				row2.createCell(j+2).setCellValue(onedata.get(datacolumn[j]));
			}
		}
	}
}
