package common.toSQL.SQLElement;

import common.toSQL.ISQLElement.IColumElement;
import common.toSQL.SQLModel.SQLElement;
import common.toSQL.SQLParameters.SQLOption;

public class ColumElement_V extends SQLElement implements IColumElement {
	private Object value;
	public ColumElement_V(String name,Object value) {
		setName(name);
		setAlias(null);
		this.value = value;
		setFlag(false);
	}
	@Override
	public String getFullName() {
		String str = "";
		if(flag) {
			str += getAlias()+"."+getName();
		}else {
			str += "\""+getAlias()+"\".\""+getName()+"\"";
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
	public String valToString() {
		String str = "";
		if(value instanceof String) {
			str += "'"+value.toString()+"'";
		}else {
			str += value.toString();
		}
		return str;
	}
	@Override
	public String toSQL() {
		return SQLOption.equals.toSQL(getFirstName(),valToString());
	}

}
