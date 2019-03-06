package common.toSQL.SQLElement;

import common.toSQL.ISQLElement.ILimitElement;

public class LimitElement  implements ILimitElement{
	private String limit = "";
	public LimitElement(int first,int size) {
		limit += "LIMIT "+size+" OFFSET "+first;
	}
	public LimitElement(int num) {
		limit += "LIMIT "+num;
	}
	@Override
	public String toSQL() {
		return limit;
	}
}
