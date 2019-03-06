package common.toSQL.ISQLElement;

import common.toSQL.SQLModel.ISQLModel;
import common.toSQL.SQLParameters.SortWay;

public interface IOrderElement extends ISQLModel{
	public void addOrder(String colum,SortWay sortway);
}
