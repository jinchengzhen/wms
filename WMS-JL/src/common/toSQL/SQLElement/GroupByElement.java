package common.toSQL.SQLElement;

import common.toSQL.ISQLElement.IGroupElement;

public class GroupByElement implements IGroupElement{
	private String group = "";
	public GroupByElement(String colum) {
		group += "GROUP BY "+colum;
	}
	@Override
	public String toSQL() {
		return group;
	}
}
