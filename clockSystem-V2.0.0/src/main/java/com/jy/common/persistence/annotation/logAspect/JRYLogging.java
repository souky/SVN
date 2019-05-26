package com.jy.common.persistence.annotation.logAspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Documented –注解是否将包含在JavaDoc中
 * Retention –什么时候使用该注解 RetentionPolicy.SOURCE阶段(java源文件阶段)，还是在RetentionPolicy.CLASS阶段(class文件阶段)，或者是在RetentionPolicy.RUNTIME阶段(内存中的字节码运行时阶段)
 * Target –注解用于什么地方
 * Inherited – 是否允许子类继承该注解
 * 
 * @author jinxiaoxiang@jrycn.cn
 * @since 2018-11-06
 * @version 1.0.0
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JRYLogging {
	
	/**
	 * 操作类型 0 == Add（新增） 1 == Edit（修改） 2 == Del（删除）
	 * 
	 * @return
	 */
	public OpType opType() default OpType.ADD;
	
	/**
	 * 操作信息
	 * 
	 * @return
	 */
	public String opInfo() default "";
	
	/**
	 * 替换参数
	 * 
	 * @return
	 */
	public String[] opReplaceParams() default "";
}
