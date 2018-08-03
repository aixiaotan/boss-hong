package com.boss.common.dto.common;

import java.io.Serializable;

public class SysDistDto implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -975182065286744529L;

	/**
     * 	数据编码	
     */
	private String datacode;
	
	 /**
     * 	数据名称/值	
     */
	private String datavalue;
	
	/**
     * 	顺序	
     */
	private Integer sortno;

	public String getDatacode() {
		return datacode;
	}

	public void setDatacode(String datacode) {
		this.datacode = datacode;
	}

	public String getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}

	public Integer getSortno() {
		return sortno;
	}

	public void setSortno(Integer sortno) {
		this.sortno = sortno;
	}

}