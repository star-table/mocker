package org.nico.mocker.config;

import org.nico.mocker.consts.RespCode;
import org.nico.mocker.exception.MockerException;
import org.nico.mocker.resp.RespVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class ExceptionConfig {

	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public RespVo<?> errorHandler(Exception ex) {
		ex.printStackTrace();
		return RespVo.failure(RespCode.FAILURE);
	}
	
	@ResponseBody
	@ExceptionHandler(value = MockerException.class)
	public RespVo<?> errorHandler(MockerException ex) {
		ex.printStackTrace();
		return RespVo.failure(ex.getRespCode());
	}
	
}
