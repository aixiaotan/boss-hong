
package com.boss.hong.webConfig;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

public class FastJsonHttpMessageConverterEx extends FastJsonHttpMessageConverter {
	
	public FastJsonHttpMessageConverterEx() {
		
		// 在这里配置 fastjson 特性
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		
		fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");   // 自定义时间格式
		
		//fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);  //正常转换 null 值
				
		this.setFastJsonConfig(fastJsonConfig);
	}

	@Override
	protected boolean supports(Class<?> clazz) {
		return super.supports(clazz);
	}
}
