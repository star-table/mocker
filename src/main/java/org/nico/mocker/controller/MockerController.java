package org.nico.mocker.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.nico.mocker.ApiContainer;
import org.nico.mocker.consts.RespCode;
import org.nico.mocker.model.Api;
import org.nico.mocker.resp.RespVo;
import org.nico.mocker.service.MockerService;
import org.nico.mocker.utils.HttpContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@io.swagger.annotations.Api("Mocker测试入口")
@RestController
@RequestMapping("/m")
public class MockerController {

	@Autowired
	private MockerService mockerService;
	
	@GetMapping("/apis")
	public Object apis(){
		try {
			return ApiContainer.getApiMap().keySet();
		} catch (Exception e) {
			e.printStackTrace();
			return RespVo.failure(RespCode.API_PRASE_ERR);
		}
	}
	
	@RequestMapping("/mock")
	public Object handle(
			@RequestParam(name = "_listSize", required = false) Integer listSize,
			@RequestParam(name = "_mapSize", required = false) Integer mapSize,
			@RequestParam(name = "_dateFormat", required = false) String dateFormat,
			HttpServletRequest request) {
		try {
			HttpContextUtils.setListSize(listSize);
			HttpContextUtils.setMapSize(mapSize);
			HttpContextUtils.setDateFormat(dateFormat);
			
			System.out.println(request.getAttribute("requestUrl"));
			
			Api api = ApiContainer.getApi(String.valueOf(request.getAttribute("requestUrl")));
			if(api == null) {
				return RespVo.failure(RespCode.API_NOT_FOUND);
			}
			return mockerService.rendering(api);
		} catch (Exception e) {
			e.printStackTrace();
			return RespVo.failure(RespCode.API_PRASE_ERR);
		}
	}
}
