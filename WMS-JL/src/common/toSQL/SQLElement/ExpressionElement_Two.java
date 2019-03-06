package common.toSQL.SQLElement;



import common.toSQL.ISQLElement.IExpressionElement;
import common.toSQL.SQLParameters.Relation;
import common.toSQL.SQLParameters.SQLOption;

public class ExpressionElement_Two implements IExpressionElement{
	protected boolean isGroup;
	protected Relation relation;
	protected String expression;
	private ExpressionElement_Two(Relation relation,boolean isGroup) {
		this.isGroup = isGroup;
		this.relation = relation;
	}
	public ExpressionElement_Two(String leftExp,SQLOption option,String rightExp,Relation relation,boolean isGroup) {
		this(relation, isGroup);
		this.expression = option.toSQL(leftExp,rightExp);
	}
	public ExpressionElement_Two(ColumElement_V colum,SQLOption option,Relation relation,boolean isGroup) {
		this(relation, isGroup);
		this.expression = option.toSQL(colum.getFirstName(),colum.valToString());
	}
	
	protected String getRelation() {
		if(relation != null) {
			return relation.toString();
		}
		return null;
	}
	@Override
	public String toSQL() {
		if(isGroup) {
			return getRelation()+" ("+expression+")";
		}
		return getRelation()+" "+expression;
	}

}
