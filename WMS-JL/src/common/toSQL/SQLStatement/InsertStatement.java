package common.toSQL.SQLStatement;

import java.util.List;

import common.jdbc.CommonDao;
import common.toSQL.ISQLStatement.IExcuteStatement;
import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLElement.ColumElement_V;
import common.toSQL.SQLModel.SQLStatement;

public class InsertStatement extends SQLStatement implements IExcuteStatement {
	private StringBuilder columNameStr ;
	private StringBuilder columValueStr;
	private List<ColumElement_V> columlist;
	
	public InsertStatement(TableBean table) {
		this.columlist = table.getColumlist_V();
		addTableName(table.getTablename());
	}
	protected void columToSQL() {
		if(columNameStr == null) {
			columNameStr = new StringBuilder("(");
			for(ColumElement_V colum : columlist) {
				columNameStr.append(colum.getFirstName()+",");
			}
			columNameStr.replace(columNameStr.length()-1, columNameStr.length(), ")");
		}
	}
	protected void valueToSQL() {
		if(columValueStr == null) {
			columValueStr = new StringBuilder();
		}else {
			columValueStr.append(",");
		}
		if(!columlist.isEmpty()) {
			columValueStr.append("(");
			for(ColumElement_V colum : columlist) {
				columValueStr.append(colum.valToString()+",");
			}
			columValueStr.replace(columValueStr.length()-1, columValueStr.length(), ")");
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
		columToSQL();
		valueToSQL();
		return "INSERT INTO "+tablelist.get(0).getFirstName()+columNameStr.toString()+"VALUES"+columValueStr.toString();
	}

}
