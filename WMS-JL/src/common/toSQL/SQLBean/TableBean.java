package common.toSQL.SQLBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import common.toSQL.ISQLElement.IExpressionElement;
import common.toSQL.SQLElement.ColumElement_N;
import common.toSQL.SQLElement.ColumElement_V;
import common.toSQL.SQLElement.ExpressionElement_Two;
import common.toSQL.SQLElement.TableElement;
import common.toSQL.SQLParameters.Relation;
import common.toSQL.SQLParameters.SQLMethods;
import common.toSQL.SQLParameters.SQLOption;

public class TableBean {
	private TableElement tablename;
	private List<ColumElement_N> columlist_N;
	private List<ColumElement_V> columlist_V;
	private List<IExpressionElement> expressionlist; 
	public TableBean(TableElement tablename) {
		this.tablename = tablename;
		expressionlist = new ArrayList<IExpressionElement>();
	}
	//colum-name && colum-alias 构造器
	public TableBean(TableElement tablename,List<String> list) {
		this(tablename);
		columlist_N = new ArrayList<ColumElement_N>();
		for(String name : list){
			ColumElement_N colum = new ColumElement_N(name,this.tablename.getAlias());
			columlist_N.add(colum);
		}
	}
	//colum-name && colum-value 构造器
	public TableBean(TableElement tablename,Map<String,Object> map) {
		this(tablename);
		columlist_V = new ArrayList<ColumElement_V>();
		for(Entry<String, Object> entry : map.entrySet()) {
			ColumElement_V colum = new ColumElement_V(entry.getKey(), entry.getValue());
			columlist_V.add(colum);
		}
	}
	//添加colum-name
	public void addColum(String name) {
		if(columlist_N == null) {
			columlist_N = new ArrayList<ColumElement_N>();
		}
		ColumElement_N colum = new ColumElement_N(name,tablename.getAlias());
		columlist_N.add(colum);
	}
	//带方法的colum
	public void addColum(SQLMethods method,String...colums) {
		if(columlist_N == null) {
			columlist_N = new ArrayList<ColumElement_N>();
		}
		ColumElement_N colum = new ColumElement_N(method.toSQL(colums),null);
		colum.setFlag(true);
		columlist_N.add(colum);
	}
	//添加colum-name && colum-value
	public void addColum(String name,Object value) {
		if(columlist_V != null) {
			ColumElement_V colum = new ColumElement_V(name,value);
			columlist_V.add(colum);
		}
	}
	//添加表达式
	public void addExpression(IExpressionElement expression) {
		expressionlist.add(expression); 
	}
	public void addSimpleExpression(String key,Object value) {
		String rightExp = "";
		if(value instanceof String) {
			rightExp = "'"+value.toString()+"'";
		}else {
			rightExp = value.toString();
		}
		IExpressionElement expression = new ExpressionElement_Two(key, SQLOption.equals, rightExp, Relation.AND, false);
		expressionlist.add(expression); 
	}
	public void addSimpleExpression(Map<String,Object> map) {
		for(Entry<String, Object> entry : map.entrySet()) {
			if(entry.getValue() != null && !"".equals(entry.getValue().toString())) {
				String rightExp = "";
				String leftExp = "";
				if(entry.getValue() instanceof String) {
					rightExp = "'"+entry.getValue().toString()+"'";
				}else {
					rightExp = entry.getValue().toString();
				}
				
				if(entry.getKey().lastIndexOf("\"") > 0) {
					leftExp = entry.getKey();
				}else {
					leftExp = "\""+entry.getKey()+"\"";
				}
				IExpressionElement expression = new ExpressionElement_Two(leftExp, SQLOption.equals, rightExp, Relation.AND, false);
				expressionlist.add(expression); 
			}
		}
	}
	//gets and sets
	public TableElement getTablename() {
		return tablename;
	}
	public void setTablename(TableElement tablename) {
		this.tablename = tablename;
	}
	public List<ColumElement_N> getColumlist_N() {
		return columlist_N;
	}
	public void setColumlist_N(List<ColumElement_N> columlist_N) {
		this.columlist_N = columlist_N;
	}
	public List<ColumElement_V> getColumlist_V() {
		return columlist_V;
	}
	public void setColumlist_V(List<ColumElement_V> columlist_V) {
		this.columlist_V = columlist_V;
	}
	public List<IExpressionElement> getExpressionlist() {
		return expressionlist;
	}
	public void setExpressionlist(List<IExpressionElement> expressionlist) {
		this.expressionlist = expressionlist;
	}
}
