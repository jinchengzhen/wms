package common.toSQL.SQLStatement;



import java.util.List;

import common.jdbc.CommonDao;
import common.toSQL.ISQLStatement.IExcuteStatement;
import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLElement.ColumElement_V;
import common.toSQL.SQLModel.SQLStatement;

public class UpdateStatement extends SQLStatement implements IExcuteStatement  {
	private StringBuilder setValueStr; 
	private List<ColumElement_V> columlist;
	public UpdateStatement(TableBean tablebean) {
		addTableName(tablebean.getTablename());
		columlist = tablebean.getColumlist_V();
		addExpressionlist(tablebean.getExpressionlist());
	}
	protected void valueToSQL() {
		if(setValueStr == null) {
			setValueStr = new StringBuilder();
			for(ColumElement_V colum : columlist) {
				setValueStr.append(colum.toSQL()+",");
			}
			setValueStr.delete(setValueStr.length()-1, setValueStr.length());
		}
	}
	@Override
	public String toSQL() {
		return null;
	}

	@Override
	public boolean excute(String sql) {
		return CommonDao.getInstance().excute(sql);
	}

	@Override
	public boolean excute() {
		return excute(this.toSQLStatement());
	}

	@Override
	public String toSQLStatement() {
		valueToSQL();
		return "UPDATE "+tablelist.get(0).getFirstName()+" SET "+setValueStr.toString()+" "+this.expressionToSQL();
	}

}
