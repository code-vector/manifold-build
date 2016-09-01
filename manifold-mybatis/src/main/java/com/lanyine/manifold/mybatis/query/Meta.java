package com.lanyine.manifold.mybatis.query;

import java.util.Collection;

/**
 * 
 * @Description:最小的条件单元
 * @author shadow
 *
 */
class Meta {
	private String property;
	private Object val1;
	private Object val2;
	private Operator operator;
	private boolean noValue;
	private boolean setValue;
	private boolean singleValue;
	private boolean betweenValue;

	/**
	 * 无条件表达式
	 * 
	 * @param val1
	 *            条件
	 * @param operator
	 *            表达式连接符
	 */
	protected Meta(String property, Operator operator) {
		this.property = property;
		this.operator = operator;
		this.noValue = true;
	}

	/**
	 * 单条件表达式
	 * 
	 * @param val1
	 *            条件
	 * @param operator
	 *            表达式连接符
	 */
	public Meta(String property, Operator operator, Object val1) {
		this.property = property;
		this.operator = operator;
		this.val1 = val1;
		if (val1 instanceof Collection<?> || val1.getClass().isArray()) {
			this.setValue = true;
		} else {
			this.singleValue = true;
		}
	}

	/**
	 * 双条件表达式
	 * 
	 * @param val1
	 *            条件1
	 * @param val2
	 *            条件2
	 * @param operator
	 *            表达式连接符
	 */
	public Meta(String property, Operator operator, Object val1, Object val2) {
		this.property = property;
		this.operator = operator;
		this.val1 = val1;
		this.val2 = val2;
		this.betweenValue = true;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getVal1() {
		return val1;
	}

	public void setVal1(Object val1) {
		this.val1 = val1;
	}

	public Object getVal2() {
		return val2;
	}

	public void setVal2(Object val2) {
		this.val2 = val2;
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public boolean isNoValue() {
		return noValue;
	}

	public void setNoValue(boolean noValue) {
		this.noValue = noValue;
	}

	public boolean isSetValue() {
		return setValue;
	}

	public void setSetValue(boolean setValue) {
		this.setValue = setValue;
	}

	public boolean isSingleValue() {
		return singleValue;
	}

	public void setSingleValue(boolean singleValue) {
		this.singleValue = singleValue;
	}

	public boolean isBetweenValue() {
		return betweenValue;
	}

	public void setBetweenValue(boolean betweenValue) {
		this.betweenValue = betweenValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (betweenValue ? 1231 : 1237);
		result = prime * result + (noValue ? 1231 : 1237);
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + (setValue ? 1231 : 1237);
		result = prime * result + (singleValue ? 1231 : 1237);
		result = prime * result + ((val1 == null) ? 0 : val1.hashCode());
		result = prime * result + ((val2 == null) ? 0 : val2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meta other = (Meta) obj;
		if (betweenValue != other.betweenValue)
			return false;
		if (noValue != other.noValue)
			return false;
		if (operator != other.operator)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (setValue != other.setValue)
			return false;
		if (singleValue != other.singleValue)
			return false;
		if (val1 == null) {
			if (other.val1 != null)
				return false;
		} else if (!val1.equals(other.val1))
			return false;
		if (val2 == null) {
			if (other.val2 != null)
				return false;
		} else if (!val2.equals(other.val2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Meta [property=" + property + ", val1=" + val1 + ", val2=" + val2 + ", operator=" + operator + ", noValue="
				+ noValue + ", setValue=" + setValue + ", singleValue=" + singleValue + ", betweenValue=" + betweenValue
				+ "]";
	}
}
