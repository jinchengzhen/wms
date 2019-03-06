package common.toSQL.SQLElement;

import common.toSQL.ISQLElement.ITableElement;
import common.toSQL.SQLModel.SQLElement;

public class TableElement extends SQLElement implements ITableElement{
	public TableElement(String name,String alias) {
		setName(name);
		setAlias(alias);
		setFlag(false);
	}
	@Override
	public String getFullName() {
		String str = "";
		if(flag) {
			str += getName()+" "+getAlias();
		}else {
			str += "\""+getName()+"\" \""+getAlias()+"\"";
		}
		return str;
	}

	@Override
	public String getFirstName() {
		String str = "";
		if(flag) {
			str += getName();
		}else {
			str += "\""+getName()+"\"";
		}
		return str;
	}

	@Override
	public String toSQL() {
		return getFullName();
	}

}
