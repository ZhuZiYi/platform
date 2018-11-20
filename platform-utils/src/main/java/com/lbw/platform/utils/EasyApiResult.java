package com.lbw.platform.utils;

import java.util.List;

public class EasyApiResult<T> extends ApiResult<T>{
	
	private Integer total = 0;
	private T rows;
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public T getRows() {
		return rows;
	}
	public void setRows(T rows) {
		this.rows = rows;
	}
	
	public EasyApiResult(){
		super();
		this.total = 0;
		this.rows = null;
	}
	
	public EasyApiResult(T t){
		setRows(t);
		
		//String clsName = t.getClass().getName();
		//String lstClsName = List.class.getName();
		if (List.class.isInstance(t))
			setTotal(((List)t).size());
	}
	
	 @Override
	    public String toString() {
	        return "ApiResult{" +
	                "rows=" + rows +
	                ", code='" + getCode() + '\'' +
	                ", msg='" + getMsg() + '\'' +
	                '}';
	    }
}
