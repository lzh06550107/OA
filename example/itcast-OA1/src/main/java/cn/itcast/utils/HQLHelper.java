package cn.itcast.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HQLHelper {
	private String fromStr;//FROM子句
	//必须要赋值为空字符串，因为当没有设置该值时，默认是null,会导致拼接
	//语句中有null出现
	private String whereStr = "";//WHERE子句
	private String orderByStr = "";//ORDER BY子句
	private List<Object> args = new ArrayList<Object>(); //封装HQL中对应的参数
	
	public HQLHelper(){}
	
	/**
	 * 通过构造方法拼接FROM子句
	 * @param clazz
	 */
	public HQLHelper(Class clazz){
		this.fromStr = " FROM " + clazz.getSimpleName() + " o ";
	}
	/**
	 * 拼接WHERE子句
	 * @param condition
	 * @param args
	 */
	public void addWhere(String condition, Object...args){
		if(this.whereStr.length() == 0){
			//第一次拼接WHERE子句
			this.whereStr = " WHERE " + condition;
		}else{
			//不是第一次拼接WHERE子句
			this.whereStr += " AND " + condition;
		}
		if(args != null && args.length > 0){
			//封装参数
			this.args.addAll(Arrays.asList(args));
		}
	}
	/**
	 * 拼接OrderBy子句
	 * @param orderBy
	 * @param asc
	 */
	public void addOrderBy(String orderBy, boolean asc){
		if(this.orderByStr.length() == 0){
			//第一次拼接ORDER BY子句
			this.orderByStr = " ORDER BY " + orderBy + (asc?" ASC " : " DESC ");
		}else{
			//不是第一次拼接
			this.orderByStr += ", " + orderBy + (asc?" ASC " : " DESC ");
		}
	}
	/**
	 * 获取查询List集合的HQL语句
	 * @return
	 */
	public String getListHQL(){
		return this.fromStr + this.whereStr + this.orderByStr;
	}
	/**
	 * 获取查询记录总条数的HQL语句
	 * @return
	 */
	public String getCountHQL(){
		return "SELECT COUNT(*) " + this.fromStr + this.whereStr; 
	}

	public List<Object> getArgs() {
		return args;
	}

	public void setArgs(List<Object> args) {
		this.args = args;
	}
}
