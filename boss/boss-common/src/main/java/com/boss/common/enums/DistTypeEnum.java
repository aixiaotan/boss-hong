package com.boss.common.enums;

/** 
 * dist表中用到的数据类型 枚举类 
 * @author Yao 
 * @create 2018年7月24日  
 */
public enum DistTypeEnum {
	
    SHOP_LABEL("ShopLabel","店铺标签");
    
    /**
     * 	数据编码	
     */
    private String datatype;
    
    /**
     * 	数据字典描述
     */
    private String desc;

    DistTypeEnum(String datatype,String desc){
    	this.datatype = datatype;
        this.desc = desc;
    }

    public static boolean containDatatype(String datatype){
        for (DistTypeEnum distType: DistTypeEnum.values()){
            if(distType.getDatatype().equals(datatype)){
                return true;
            }
        }
        return false;
    }

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
