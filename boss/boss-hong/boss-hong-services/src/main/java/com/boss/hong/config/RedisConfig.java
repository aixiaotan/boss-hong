//package com.boss.hong.config;
//
//import org.springframework.beans.factory.annotation.Configurable;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import com.boss.hong.serializer.RedisObjectSerializer;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Configurable
//public class RedisConfig {
//	
//	@Bean
//	public RedisTemplate<String, UserMainPO> redisTemplate1(RedisConnectionFactory factory) {
//		RedisTemplate<String, UserMainPO> redisTemplate = new RedisTemplate<String, UserMainPO>();
//		((JedisConnectionFactory)factory).setDatabase(1);
//		redisTemplate.setConnectionFactory(factory);
//		redisTemplate.afterPropertiesSet();
//		setSerializer(redisTemplate);
//		return redisTemplate;
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private void setSerializer(RedisTemplate<String, UserMainPO> template) {
//		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//		ObjectMapper om = new ObjectMapper();
//		om.setVisibility(PropertyAccessor.ALL, com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//		jackson2JsonRedisSerializer.setObjectMapper(om);
//		template.setKeySerializer(new StringRedisSerializer());
//		template.setValueSerializer(new RedisObjectSerializer());
//	}
//	
//
//}
