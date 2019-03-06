package common.toSQL.SQLModel;

import common.toSQL.SQLModel.SQLModel;

public abstract class SQLElement extends SQLModel{
	protected String name;
	protected String alias;
	protected boolean flag;
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAlias(String alias) {
		if(alias == null) {
			this.alias = "";
		}else {
			this.alias = alias;
		}
	}
	public String getAlias() {
		return alias;
	}
	protected String getName() {
		return name;
	}
}
