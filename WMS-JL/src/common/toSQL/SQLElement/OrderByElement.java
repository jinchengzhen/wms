package common.toSQL.SQLElement;


import common.toSQL.ISQLElement.IOrderElement;
import common.toSQL.SQLParameters.SortWay;

public class OrderByElement implements IOrderElement{
	private StringBuilder orderby = new StringBuilder("ORDER BY");
	private boolean flag;
	public OrderByElement() {
		flag = false;
	}
	public void addOrder(String colum,SortWay sortway) {
		orderby.append(" "+sortway.toSQL(colum)+",");
		flag = true;
	}
	@Override
	public String toSQL() {
		if(flag) {
			return orderby.delete(orderby.length()-1, orderby.length()).toString();
		}
		return "";
	}
}
