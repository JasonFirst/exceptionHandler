package com.jason.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jason.enumeration.ErrorMsgEnum;
import com.jason.exception.DataNotCompleteException;
import com.jason.json.JsonResponse;
import com.jason.service.IExampleService;

@Controller
public class TestController {
	
	@Autowired
	private IExampleService exampleService;
	
	@RequestMapping("test/run/old")
	public JsonResponse testRunOld() {
		try {
			exampleService.runTest();
			System.out.println("正常运行");
			return JsonResponse.newOk();
		}catch (DataNotCompleteException e) {
			return JsonResponse.newError(ErrorMsgEnum.DATA_NO_COMPLETE);
		} catch (Exception e) {
			return JsonResponse.newError();
		}
		
	}
	
	@RequestMapping("test/run/aop")
	public JsonResponse testRunAop() throws Exception {
		exampleService.runTest();
		System.out.println("正常运行");
		return JsonResponse.newOk();
	}
}
