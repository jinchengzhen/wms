package common.toSQL.SQLParameters;


import common.toSQL.SQLModel.ISQLParameters;

public enum SortWay implements ISQLParameters{
	ASC(){
		@Override
		public String toSQL(String... params) {
			return params[0]+" ASC";
		}
	},DESC(){
		@Override
		public String toSQL(String... params) {
			return params[0]+" DESC";
		}
	};
	@Override
	public String toSQL(String... params) {
		return null;
	}
	
}
