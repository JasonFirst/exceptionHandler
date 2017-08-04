package com.jason.aop;


import java.util.Objects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.jason.enumeration.ErrorMsgEnum;
import com.jason.exception.CustomException;
import com.jason.json.JsonResponse;


/**
 * 控制层异常处理 捕获到异常时新建一个ResultVo 获得方法返回类型，用这个类型新建对象，复制ResultVo的属性给新建对象后返回
 * 
 * 自定义异常 CustomException 支持两种类型，构造参数使用 ERROR_MSG 或者 String
 */
@Aspect
@Component
public class ExceptionHandleAop {

	private static final Logger logger = Logger.getLogger(ExceptionHandleAop.class);

	@Around("execution(public * com.jason.*.controller..*.*(..))")
	public JsonResponse serviceAOP(ProceedingJoinPoint pjp) throws Exception {

		JsonResponse newResultVo = null;

		try {
			return (JsonResponse) pjp.proceed();
		} catch (CustomException e) {
			logger.info("自定义业务异常:" + e.getMessage());
			ErrorMsgEnum errorMsgEnum = e.getErrorMsgEnum();
			if (Objects.nonNull(errorMsgEnum)) {
				newResultVo = JsonResponse.newError(errorMsgEnum);
			} else {
				newResultVo = JsonResponse.newError(e.getMessage());	
			}
		} catch (Throwable e) {
			logger.error("出现运行时异常：", e);
			newResultVo = JsonResponse.newError();
		}

		return newResultVo;

	}

}
