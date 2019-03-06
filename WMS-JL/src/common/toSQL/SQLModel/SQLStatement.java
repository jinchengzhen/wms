package common.toSQL.SQLModel;

import java.util.ArrayList;
import java.util.List;


import common.toSQL.ISQLElement.IExpressionElement;
import common.toSQL.ISQLElement.ITableElement;


public abstract class SQLStatement extends SQLModel{
	protected List<ITableElement> tablelist = new ArrayList<ITableElement>();
	protected List<IExpressionElement> expressionlist = new ArrayList<IExpressionElement>();
	
	public void addTableName(ITableElement tablename) {
		this.tablelist.add(tablename);
	}
	public void addExpression(IExpressionElement expression) {
		this.expressionlist.add(expression);
	}
	public void addExpressionlist(List<IExpressionElement> expressionlist) {
		this.expressionlist.addAll(expressionlist);
	}
	protected String tableToSQL() {
		if(!tablelist.isEmpty()) {
			StringBuilder result = new StringBuilder();
			for(ITableElement table:tablelist) {
				result.append(table.toSQL()+",");
			}
			result.delete(result.length()-1, result.length());
			return result.toString();
		}
		return "";
	}
	protected String expressionToSQL() {
		if(!expressionlist.isEmpty()) {
			StringBuilder result = new StringBuilder(" WHERE 1=1 ");
			for(IExpressionElement expression:expressionlist) {
				result.append(expression.toSQL()+" ");
			}
			result.delete(result.length()-1, result.length());
			return result.toString();
		}
		return "";
	}
	public abstract String toSQLStatement(); 
}
