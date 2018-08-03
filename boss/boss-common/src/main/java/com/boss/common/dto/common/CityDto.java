package com.boss.common.dto.common;

import java.io.Serializable;

public class CityDto implements Serializable{

	private static final long serialVersionUID = 4619086787018958992L;

	/**
	 * 城市id
	 */
	private Integer cityid;
	
	/**
	 * 省份id
	 */
	private Integer provinceid;
	
	/**
	 * 城市名
	 */
	private String cityname;

	public Integer getCityid() {
		return cityid;
	}

	public void setCityid(Integer cityid) {
		this.cityid = cityid;
	}

	public Integer getProvinceid() {
		return provinceid;
	}

	public void setProvinceid(Integer provinceid) {
		this.provinceid = provinceid;
	}

	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	
}
