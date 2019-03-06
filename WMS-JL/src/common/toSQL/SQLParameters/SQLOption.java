package common.toSQL.SQLParameters;

import common.toSQL.SQLModel.ISQLParameters;

public enum SQLOption implements ISQLParameters{
	equals(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" = "+params[1];
			}else {
				return "";
			}
		}
		
	},lessthan(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" < "+params[1];
			}else {
				return "";
			}
		}
	}
	,morethan(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" > "+params[1];
			}else {
				return "";
			}
		}
	},lessANDequal(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" <= "+params[1];
			}else {
				return "";
			}
		}
	},moreANDequal(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" >= "+params[1];
			}else {
				return "";
			}
		}
	},notequal(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" != "+params[1];
			}else {
				return "";
			}
		}
	},likeForward(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" LIKE '%"+params[1]+"'";
			}else {
				return "";
			}
		}
	},likeAfter(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" LIKE '"+params[1]+"%'";
			}else {
				return "";
			}
		}
	},likeALL(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 2) {
				return params[0]+" LIKE '%"+params[1]+"%'";
			}else {
				return "";
			}
		}
	},between(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 3) {
				return params[0]+" BETWEEN "+params[1]+" AND "+params[2];
			}else {
				return "";
			}
		}
	},RegExp(){
		@Override
		public String toSQL(String... params) {
			if(params.length >= 1) {
				return "";
			}else {
				return "";
			}
		}
	},isNULL(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 1) {
				return params[0]+" IS NULL";
			}else {
				return "";
			}
		}
	},notNULL(){
		@Override
		public String toSQL(String... params) {
			if(params.length == 1) {
				return params[0]+" IS NOT NULL";
			}else {
				return "";
			}
		}
	};

	@Override
	public String toSQL(String... params) {
		// TODO Auto-generated method stub
		return null;
	}

}
