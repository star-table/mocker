package org.nico.mocker.plugins;

import java.util.List;

import org.nico.mocker.model.Api;
import org.nico.mocker.model.Plugin;


public interface AbstractPluginHandler {

	public List<Api> extract(Plugin plugin) throws Exception;
}
