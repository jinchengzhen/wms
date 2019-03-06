package common.toSQL.SQLParameters;


import common.toSQL.SQLModel.ISQLParameters;

public enum SQLMethods implements ISQLParameters{
	COUNT{
		@Override
		public String toSQL(String... params) {
			return "COUNT(\""+params[0]+"\")";
		}
	};
	@Override
	public String toSQL(String... params) {
		return null;
	}

}
