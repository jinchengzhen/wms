package common.toSQL.SQLStatement;

import common.jdbc.CommonDao;
import common.toSQL.ISQLStatement.IExcuteStatement;
import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLModel.SQLStatement;

public class DeleteStatement extends SQLStatement implements IExcuteStatement  {
	public DeleteStatement(TableBean tablebean) {
		addTableName(tablebean.getTablename());
		if(tablebean.getExpressionlist() != null && !tablebean.getExpressionlist().isEmpty()) {
			this.addExpressionlist(tablebean.getExpressionlist());
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
		return "DELETE FROM "+tablelist.get(0).getFirstName()+this.expressionToSQL();
	}

}
