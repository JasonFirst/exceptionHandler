package com.jason.service.impl;

import org.springframework.stereotype.Service;

import com.jason.enumeration.ErrorMsgEnum;
import com.jason.exception.CustomException;
import com.jason.service.IExampleService;

@Service
public class ExampleService implements IExampleService{

	@Override
	public void runTest() throws Exception {

		// do something
		System.out.println("run something");
		throw new CustomException(ErrorMsgEnum.DATA_NO_COMPLETE);
	}

}
