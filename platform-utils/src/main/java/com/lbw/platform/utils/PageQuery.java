package com.lbw.platform.utils;

public class PageQuery<T> {

	private int pageNum=1;
	private int pageSize=10;
	private String pageNumState="1"; // 1 当前页 2 上一页 3 下一页
	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}

	public String getPageNumState() {
		return pageNumState;
	}

	public void setPageNumState(String pageNumState) {
		if(pageNumState.equals("2")){
			this.pageNum=this.pageNum-1;
		}
		if(pageNumState.equals("3")){
			this.pageNum=this.pageNum+1;
		}
		//防止Foregin 二次调用 增加两页
		pageNumState="1";
		this.pageNumState = pageNumState;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
