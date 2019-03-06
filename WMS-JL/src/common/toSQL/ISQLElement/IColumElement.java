package common.toSQL.ISQLElement;

import common.toSQL.SQLModel.ISQLModel;

public interface IColumElement extends ISQLModel{
	public abstract String getFullName();
	public abstract String getFirstName();
}
