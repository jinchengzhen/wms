package common.toSQL.ISQLStatement;

import common.toSQL.SQLModel.ISQLModel;

public interface IExcuteStatement extends ISQLModel{
	public boolean excute(String sql);
	public boolean excute();
}
