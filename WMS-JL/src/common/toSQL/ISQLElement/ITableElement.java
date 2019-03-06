package common.toSQL.ISQLElement;

import common.toSQL.SQLModel.ISQLModel;

public interface ITableElement extends ISQLModel{
	public abstract String getFullName();
	public abstract String getFirstName();
}
