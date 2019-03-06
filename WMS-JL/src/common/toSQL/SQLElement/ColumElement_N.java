package common.toSQL.SQLElement;

import common.toSQL.ISQLElement.IColumElement;
import common.toSQL.SQLModel.SQLElement;

public class ColumElement_N extends SQLElement implements IColumElement{

	public ColumElement_N(String name,String alias) {
		setName(name);
		setAlias(alias);
		setFlag(false);
	}
	@Override
	public String getFullName() {
		String str = "";
		if("".equals(getAlias())) {
			if(flag) {
				str += getName();
			}else {
				str += "\""+getName()+"\"";
			}
		}else {
			if(flag) {
				str += getAlias()+"."+getName();
			}else {
				str += "\""+getAlias()+"\".\""+getName()+"\"";
			}
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
