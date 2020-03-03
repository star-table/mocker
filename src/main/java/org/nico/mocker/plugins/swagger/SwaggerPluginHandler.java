package org.nico.mocker.plugins.swagger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.nico.mocker.enums.ApiParameterType;
import org.nico.mocker.enums.HttpMethod;
import org.nico.mocker.exception.MockerException;
import org.nico.mocker.exception.NotSupportPluginException;
import org.nico.mocker.model.Api;
import org.nico.mocker.model.ApiHeader;
import org.nico.mocker.model.ApiParameter;
import org.nico.mocker.model.Plugin;
import org.nico.mocker.plugins.AbstractPluginHandler;
import org.nico.mocker.utils.FileUtils;
import org.nico.mocker.utils.HttpUtils;
import org.nico.mocker.utils.MapUtils;
import org.springframework.util.CollectionUtils;

import com.google.gson.Gson;


public class SwaggerPluginHandler implements AbstractPluginHandler{

	private static final String SWAGGER_DEFINITION_PREFIX = "#/definitions/";
	
	private static final String SWAGGER_RESPONSE_PREFIX = "#/responses/";
	
	@Override
	public List<Api> extract(Plugin plugin) throws MockerException, IOException{
		if(! (plugin instanceof SwaggerPlugin)) {
			throw new NotSupportPluginException();
		}
		SwaggerPlugin swaggerPlugin = (SwaggerPlugin) plugin;
		return extract(swaggerPlugin);
	}

	private List<Api> extract(SwaggerPlugin plugin) throws MockerException, IOException{
		String docs = extractDocs(plugin);
		if(StringUtils.isBlank(docs)) {
			throw new MockerException("docs is blank.");
		}
		List<Api> apis = new ArrayList<Api>();
		
		Gson gson = new Gson();
		SwaggerApi swaggerApi = gson.fromJson(docs, SwaggerApi.class);
		if(! CollectionUtils.isEmpty(swaggerApi.getPaths())) {
			
			Map<String, SwaggerObject> definitions = swaggerApi.getDefinitions();
			Map<String, SwaggerResponse> responses = swaggerApi.getResponses();
			
			List<ApiParameter> apiParameters = assemblyDefinitions(definitions);
			Map<String, ApiParameter> apiParmeterMap = MapUtils.toMap(parameter -> {
				return SWAGGER_DEFINITION_PREFIX + parameter.getName();
			}, apiParameters);
			
			List<ApiParameter> apiReponses = assemblyResponses(responses, apiParmeterMap);
			Map<String, ApiParameter> responsesMap = MapUtils.toMap(parameter -> {
				return SWAGGER_RESPONSE_PREFIX + parameter.getName();
			}, apiReponses);
			
			//merge
			apiParmeterMap.putAll(responsesMap);
			assemblyApiParameterFields(apiParmeterMap, definitions);
			
			swaggerApi.getPaths().forEach((url, paths) -> {
				if(! CollectionUtils.isEmpty(paths)) {
					paths.forEach((method, path) -> {
						Api api = new Api();
						api.setDesc(path.getDescription());
						api.setMethod(HttpMethod.parse(method));
						api.setPath(url);
						api.setHeaders(assemblyApiHeader(path, definitions));
						api.setQueryParameters(assemblyQueryParameters(path, definitions, apiParmeterMap));
						api.setFormParameters(assemblyFormParameters(path, definitions, apiParmeterMap));
						
						if(! CollectionUtils.isEmpty(path.getResponses())) {
							Map<String, ApiParameter> pathResponses = new HashMap<String, ApiParameter>();
							path.getResponses().forEach((code, response) -> {
								pathResponses.put(code, assemblyApiReponse(response, apiParmeterMap));
							});
							api.setResponses(pathResponses);
						}
						apis.add(api);
					});
				}
			});
		}

		return apis;
	}

	private List<ApiHeader> assemblyApiHeader(SwaggerPath path, Map<String, SwaggerObject> definitions){
		List<ApiHeader> headers = new ArrayList<ApiHeader>();
		if(! CollectionUtils.isEmpty(path.getParameters())) {
			headers = path.getParameters()
					.stream()
					.filter(parameter -> "header".equals(parameter.getIn()))
					.map(parameter -> new ApiHeader(parameter.getName(), parameter.isRequired()))
					.collect(Collectors.toList());
		}
		return headers;
	}

	private Map<String, ApiParameter> assemblyQueryParameters(SwaggerPath path, Map<String, SwaggerObject> definitions, Map<String, ApiParameter> apiParmeterMap){
		List<ApiParameter> parameters = new ArrayList<ApiParameter>();
		if(! CollectionUtils.isEmpty(path.getParameters())) {
			parameters = path.getParameters()
					.stream()
					.filter(parameter -> "query".equals(parameter.getIn()))
					.map(parameter -> {
						ApiParameter apiParameter = new ApiParameter();
						apiParameter.setExtra(assemblySwaggerSchema(parameter.getItems(), apiParmeterMap));
						apiParameter.setName(parameter.getName());
						apiParameter.setDescription(parameter.getDescription());
						apiParameter.setRequired(parameter.isRequired());
						apiParameter.setType(ApiParameterType.get(parameter.getType()));
						return apiParameter;
					})
					.collect(Collectors.toList());
		}
		return MapUtils.toMap(ApiParameter::getName, parameters);
	}

	private Map<String, ApiParameter> assemblyFormParameters(SwaggerPath path, Map<String, SwaggerObject> definitions, Map<String, ApiParameter> apiParmeterMap){
		List<ApiParameter> parameters = new ArrayList<ApiParameter>();
		if(! CollectionUtils.isEmpty(path.getParameters())) {
			parameters = path.getParameters()
					.stream()
					.filter(parameter -> "form".equals(parameter.getIn()))
					.map(parameter -> {
						ApiParameter apiParameter = new ApiParameter();
						apiParameter.setExtra(assemblySwaggerSchema(parameter.getItems(), apiParmeterMap));
						apiParameter.setName(parameter.getName());
						apiParameter.setDescription(parameter.getDescription());
						apiParameter.setRequired(parameter.isRequired());
						apiParameter.setType(ApiParameterType.get(parameter.getType()));
						return apiParameter;
					})
					.collect(Collectors.toList());
		}
		return MapUtils.toMap(ApiParameter::getName, parameters);
	}
	
	private ApiParameter assemblyApiReponse(SwaggerResponse response, Map<String, ApiParameter> apiParmeterMap) {
		handleResponse(response);
		return assemblySwaggerSchema(response.getSchema(), apiParmeterMap);
	}

	private List<ApiParameter> assemblyDefinitions(Map<String, SwaggerObject> definitions){
		List<ApiParameter> apiParameters = new ArrayList<ApiParameter>();
		if(! CollectionUtils.isEmpty(definitions)) {
			definitions.forEach((name, info) -> {
				ApiParameter apiParameter = new ApiParameter();
				apiParameter.setName(name);
				apiParameter.setDescription(info.getDescription());
				apiParameter.setType(ApiParameterType.get(info.getType()));
				apiParameters.add(apiParameter);
			});
		}
		return apiParameters;
	}
	
	private List<ApiParameter> assemblyResponses(Map<String, SwaggerResponse> responses, Map<String, ApiParameter> apiParmeterMap){
		List<ApiParameter> apiParameters = new ArrayList<ApiParameter>();
		if(! CollectionUtils.isEmpty(responses)) {
			responses.forEach((name, response) -> {
				handleResponse(response);
				ApiParameter responseParameter = assemblySwaggerSchema(response.getSchema(), apiParmeterMap);
				if(responseParameter != null) {
					responseParameter.setName(name);
					apiParameters.add(responseParameter);
				}
			});
		}
		return apiParameters;
	}

	private List<ApiParameter> assemblyApiParameterFields(Map<String, ApiParameter> apiParmeterMap, Map<String, SwaggerObject> definitions){
		if(! CollectionUtils.isEmpty(apiParmeterMap) && ! CollectionUtils.isEmpty(definitions)) {
			apiParmeterMap.values().forEach(apiParameter -> {
				SwaggerObject definition = definitions.get(apiParameter.getName());
				if(definition != null) {
					Map<String, SwaggerObject> properties = definition.getProperties();
					if(! CollectionUtils.isEmpty(properties)) {
						Map<String, ApiParameter> fields = new HashMap<String, ApiParameter>();
						properties.forEach((k, v) -> {
							fields.put(k, assemblyApiParameterField(k, v, apiParmeterMap, definitions));
						});
						apiParameter.setFields(fields);
					}
				}
			});
		}
		return null;
	}
	
	private ApiParameter assemblyApiParameterField(String name, SwaggerObject property, Map<String, ApiParameter> apiParmeterMap, Map<String, SwaggerObject> definitions){
		String type = property.getType();
		if(StringUtils.isEmpty(type)) {
			type = "object";
		}
		ApiParameter field = new ApiParameter();
		field.setDescription(property.getDescription());
		switch (type) {
		case "object": 
			if(StringUtils.isNotBlank(property.getRef())){
				field = apiParmeterMap.get(property.getRef());
				if(field == null) {
					System.out.println(1);
				}
			}else if(property.getAdditionalProperties() != null) {
				field.setExtra(assemblySwaggerSchema(property.getAdditionalProperties(), apiParmeterMap));
				field.setType(ApiParameterType.MAP);
			}else {
				field.setType(ApiParameterType.MAP);
				field.setExtra(ApiParameter.OBJECT);
			}
			break;
		case "array":
			field.setType(ApiParameterType.ARRAY);
			field.setExtra(assemblySwaggerSchema(property.getItems(), apiParmeterMap));
			break;
		default:
			field.setType(ApiParameterType.get(type));
			break;
		}
		return field;
	}
	
	private ApiParameter assemblySwaggerSchema(SwaggerSchema schema, Map<String, ApiParameter> apiParmeterMap) {
		if(schema == null) {
			return null;
		}
		
		String type = schema.getType();
		if(StringUtils.isEmpty(type)) {
			type = "object";
		}
		
		ApiParameter field = new ApiParameter();
		switch (type) {
		case "object": 
			if(StringUtils.isNotBlank(schema.getRef())) {
				field = apiParmeterMap.get(schema.getRef());
			}else if(schema.getAdditionalProperties() != null){
				field.setType(ApiParameterType.MAP);
				field.setExtra(assemblySwaggerSchema(schema.getAdditionalProperties(), apiParmeterMap));
			}else {
				field.setType(ApiParameterType.MAP);
				field.setExtra(ApiParameter.OBJECT);
			}
			break;
		case "array":
			field.setType(ApiParameterType.ARRAY);
			field.setExtra(assemblySwaggerSchema(schema.getItems(), apiParmeterMap));
			break;
		default:
			field.setType(ApiParameterType.get(type));
			break;
		}
		
		return field;
	}

	private String extractDocs(SwaggerPlugin plugin) throws MockerException, IOException{
		if(plugin.getPathType() == null){
			throw new MockerException("Swagger plugin path type is null.");	
		}
		switch (plugin.getPathType()) {
		case HTTP:
			return HttpUtils.sendGet(plugin.getPath());
		case FILE:
			return FileUtils.read(plugin.getPath());
		default:
			throw new MockerException("Not support swagger plugin path type: " + plugin.getPathType());
		}
	}
	
	private void handleResponse(SwaggerResponse response) {
		if(response.getSchema() == null && StringUtils.isNotBlank(response.getRef())) {
			response.setSchema(new SwaggerSchema(response.getRef()));
		}
		if(response.getSchema() != null && StringUtils.isBlank(response.getRef())) {
			response.setRef(response.getSchema().getRef());
		}
	}
	
}
