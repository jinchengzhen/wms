package common.toSQL.SQLBean;

import java.util.ArrayList;
import java.util.List;

import common.jdbc.CommonDao;
import common.toSQL.SQLElement.ColumElement_N;
import common.toSQL.SQLElement.TableElement;



/**
 * @className ColumList
 * @author jincz
 * @description  获取字段名称
 * @date 2018年10月24日下午10:26:09
 */
public class TableColum {
	private List<String> list = new ArrayList<String>(); 
	private List<ColumElement_N> list_n = new ArrayList<ColumElement_N>(); 
	private Integer size;
	public int getSize() {
		if(list == null) {
			size = 0;
		}else {
			size = list.size();
		}
		return size;
	}
	private TableElement tableName;
	//从数据库取字段名
	public TableColum(TableElement tableName) {
		this.tableName = tableName;
		String tablename = tableName.getFirstName();
		String table = tablename.indexOf(".")>0?tablename.substring(tablename.indexOf(".")+1, tablename.length()):tablename;
		table = table.replaceAll("\"", "'");
		String sql = "select column_name from information_schema.columns where table_name="+table;
		List<Object[]> result = CommonDao.getInstance().selectToObj(sql);
		if(!result.isEmpty()) {
			for(Object[] array : result) {
				if(array[0] != null) {
					list.add(array[0].toString());
					list_n.add(new ColumElement_N(array[0].toString(), this.tableName.getAlias()));
				}
			}
		}
		
	}
//	select column_name,column_comment,data_type from information_schema.columns where table_name='student'
	//获取指定的字段
	public String getSingleColum(int num) {
		return list.get(num);
	}
	public String getSingleColumWithQuotes(int num) {
		return "\""+list.get(num)+"\"";
	}
	public ColumElement_N getSingleColum_N(int num) {
		return list_n.get(num);
	}
	//获取指定的字段list集合
	public List<String> getMultiColum(int...array){
		List<String> result = new ArrayList<String>();
		if(array.length<list.size()) {
			for(int i=0;i<array.length;i++) {
				result.add(list.get(array[i]));
			}
		}
		return result;
	}
	public List<ColumElement_N> getMultiColum_N(int...array){
		List<ColumElement_N> result = new ArrayList<ColumElement_N>();
		if(array.length<list_n.size()) {
			for(int i=0;i<array.length;i++) {
				result.add(list_n.get(array[i]));
			}
		}
		return result;
	}
	public List<String> getAllColumsName(){
		return list;
	}
	public TableElement getTableName() {
		return tableName;
	}
}
