package common.toSQL.SQLStatement;


import java.util.List;

import common.jdbc.CommonDao;
import common.toSQL.ISQLElement.IGroupElement;
import common.toSQL.ISQLElement.ILimitElement;
import common.toSQL.ISQLElement.IOrderElement;
import common.toSQL.ISQLStatement.ISelectStatement;
import common.toSQL.SQLBean.TableBean;
import common.toSQL.SQLElement.ColumElement_N;
import common.toSQL.SQLElement.GroupByElement;
import common.toSQL.SQLElement.LimitElement;
import common.toSQL.SQLElement.OrderByElement;
import common.toSQL.SQLModel.SQLStatement;
import common.toSQL.SQLParameters.DataType;
import common.toSQL.SQLParameters.SortWay;

public class SelectStatement extends SQLStatement implements ISelectStatement{
	private ILimitElement limit;
	private IOrderElement order;
	private IGroupElement group;
	private boolean isOrder;
	protected List<ColumElement_N> columlist;
	public SelectStatement() {
		order = new OrderByElement();
		isOrder = false;
	}
	public SelectStatement(TableBean table) {
		this();
		this.columlist = table.getColumlist_N();
		addTableName(table.getTablename());
		addExpressionlist(table.getExpressionlist());
	}
	//设置排序
	public void setOrderElement_DESC(ColumElement_N colum) {
		order.addOrder(colum.getFullName(), SortWay.DESC);
		isOrder = true;
	}
	public void setOrderElement_ASC(ColumElement_N colum) {
		order.addOrder(colum.getFullName(), SortWay.ASC);
		isOrder = true;
	}
	//设置分页
	public void setLimitElement(int first, int size) {
		limit = new LimitElement(first, size);
	}
	public void setLimitElement(int num) {
		limit = new LimitElement(num);
	}
	//设置分组
	public void setGroupElement(ColumElement_N colum) {
		group = new GroupByElement(colum.getFullName());
	}
	//字段toSQL
	protected String columToSQL() {
		if(!columlist.isEmpty()) {
			StringBuilder result = new StringBuilder();
			for(ColumElement_N colum:columlist) {
				result.append(colum.getFullName()+",");
			}
			result.delete(result.length()-1, result.length());
			return result.toString();
		}
		return "";
	}
	@Override
	public String toSQL() {
		return null;
	}

	@Override
	public List<Object[]> excute(String sql) {
		return CommonDao.getInstance().selectToObj(sql);
	}

	@Override
	public Object excute(DataType datatype) {
		Object result = null;
		switch (datatype) {
		case Json:
			result = CommonDao.getInstance().selectToJson(this);
			break;
		case Map:
			result = CommonDao.getInstance().selectToMap(this);
			break;
		case Object:
			result = CommonDao.getInstance().selectToObj(this.toSQLStatement());
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public String toSQLStatement() {
		String groupstr = group == null ? "":group.toSQL();
		String limitstr = this.limit == null ? "":this.limit.toSQL();
		return "SELECT "+this.columToSQL()+" FROM "+this.tableToSQL()+this.expressionToSQL()+" "+order.toSQL()+" "+groupstr+" "+limitstr;
	}
	public List<ColumElement_N> getColumlist() {
		return columlist;
	}
	public boolean isOrder() {
		return isOrder;
	}

}
