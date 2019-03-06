package common.toSQL.ISQLStatement;


import common.toSQL.SQLModel.ISQLModel;
import common.toSQL.SQLParameters.DataType;
public interface ISelectStatement extends ISQLModel{
	public Object excute(String sql);
	public Object excute(DataType type);
}
