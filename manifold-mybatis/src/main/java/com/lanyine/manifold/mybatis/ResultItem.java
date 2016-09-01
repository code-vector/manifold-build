package com.lanyine.manifold.mybatis;

/**
 * Mapper映射文件的resultMap映射，记录数据表(字段)<--->持久化对象属性之间的关系
 * 
 * @author shadow
 * @Date 2015年8月12日 下午9:57:40
 *
 */
public class ResultItem {
	/**
	 * 持久化对象属性名
	 */
	private String property;
	/**
	 * 数据库字段名
	 */
	private String column;
	/**
	 * 数据库字段对应的类型
	 */
	private String jdbcType;
	/**
	 * 是否是主键
	 */
	private boolean primaryKey;

	public ResultItem() {
	}

	public ResultItem(String property, String column, String jdbcType, boolean primaryKey) {
		super();
		this.property = property;
		this.column = column;
		this.jdbcType = jdbcType;
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the 持久化对象属性名
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param 持久化对象属性名
	 *            the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return the 数据库字段名
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param 数据库字段名
	 *            the column to set
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * @return the 数据库字段对应的类型
	 */
	public String getJdbcType() {
		return jdbcType;
	}

	/**
	 * @param 数据库字段对应的类型
	 *            the jdbcType to set
	 */
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	/**
	 * @return the 是否是主键
	 */
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param 是否是主键
	 *            the primaryKey to set
	 */
	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public boolean isNumberType() {
		return "INTEGER|SMALLINT|DECIMAL|FLOAT|TINYINT|BIGINT|NUMERIC".contains(getJdbcType().toUpperCase());
	}

	public boolean isTimeType() {
		return "DATE|TIME|DATETIME|TIMESTAMP".contains(getJdbcType().toUpperCase());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((column == null) ? 0 : column.hashCode());
		result = prime * result + ((jdbcType == null) ? 0 : jdbcType.hashCode());
		result = prime * result + (primaryKey ? 1231 : 1237);
		result = prime * result + ((property == null) ? 0 : property.hashCode());
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
		ResultItem other = (ResultItem) obj;
		if (column == null) {
			if (other.column != null)
				return false;
		} else if (!column.equals(other.column))
			return false;
		if (jdbcType == null) {
			if (other.jdbcType != null)
				return false;
		} else if (!jdbcType.equals(other.jdbcType))
			return false;
		if (primaryKey != other.primaryKey)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[property=" + property + ", column=" + column + ", jdbcType=" + jdbcType + ", primaryKey=" + primaryKey
				+ "]";
	}

}
