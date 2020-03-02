package org.nico.mocker.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.nico.mocker.consts.RespCode;
import org.nico.mocker.container.ApiContainer;
import org.nico.mocker.container.DataContainer;
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
	
	@RequestMapping(value = "/mock", produces = "application/json;charset=utf-8")
	public Object handle(
			@RequestParam(name = "_listSize", required = false) Integer listSize,
			@RequestParam(name = "_mapSize", required = false) Integer mapSize,
			@RequestParam(name = "_dateFormat", required = false) String dateFormat,
			@RequestParam(name = "_version", required = false) String version,
			HttpServletRequest request) {
		try {
			HttpContextUtils.setListSize(listSize);
			HttpContextUtils.setMapSize(mapSize);
			HttpContextUtils.setDateFormat(dateFormat);
			
			String apiPath = String.valueOf(request.getAttribute("requestUrl"));
			if(DataContainer.isEnable() && StringUtils.isNotBlank(version)) {
				return DataContainer.getMockData(apiPath, version);
			}else {
				Api api = ApiContainer.getApi(apiPath);
				if(api == null) {
					return RespVo.failure(RespCode.API_NOT_FOUND);
				}
				return mockerService.rendering(api);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return RespVo.failure(RespCode.API_PRASE_ERR);
		}
	}
}
