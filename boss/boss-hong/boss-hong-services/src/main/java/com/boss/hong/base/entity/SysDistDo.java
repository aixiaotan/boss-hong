package com.boss.hong.base.entity;

import com.boss.common.dto.common.EntityDto;

public class SysDistDo extends EntityDto{

    /**
	 * 
	 */
	private static final long serialVersionUID = -975182065286744529L;

	/**
     * 主键
     */
	private Integer id;
	
	/**
     * 	父ID	
     */
	private Integer pid;
	
	/**
     * 	数据类别	
     */
	private String datatype;
	
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
	
	/**
     * 	状态	
     */
	private Integer status;
	
	 /**
     * 数据类别描述
     */
	private String datadesc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDatadesc() {
		return datadesc;
	}

	public void setDatadesc(String datadesc) {
		this.datadesc = datadesc;
	}
	
}