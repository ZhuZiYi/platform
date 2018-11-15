package com.lbw.domain;

public class SysValidateCode {
	private int id;
	private String key;
	private String vCode;
	private long expireTime;
	
	public SysValidateCode(){
	}
	
	public SysValidateCode(String key,String vCode,long expireTime){
		this.key = key;
		this.vCode = vCode;
		this.expireTime = expireTime;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getvCode() {
		return vCode;
	}
	public void setvCode(String vCode) {
		this.vCode = vCode;
	}
	public long getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(int expireTime) {
		this.expireTime = expireTime;
	}

}
