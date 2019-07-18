package org.nico.codegenerator.render;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.nico.codegenerator.parser.entity.Data;
import org.nico.codegenerator.render.function.PrintCapitalize;
import org.nico.codegenerator.render.function.PrintHump;

public class TemplateRender {

	private GroupTemplate gt;
	
	private volatile static TemplateRender instance;
	
	private TemplateRender() throws IOException {
		init();
	}
	
	public static TemplateRender getInstance() throws IOException {
		if(instance == null) {
			synchronized (TemplateRender.class) {
				if(instance == null) {
					instance = new TemplateRender();
				}
			}
		}
		return instance;
	}
	
	public void init() throws IOException {
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = Configuration.defaultConfiguration();
		gt = new GroupTemplate(resourceLoader, cfg);
		gt.registerFunction("printHump", new PrintHump());
		gt.registerFunction("printCapitalize", new PrintCapitalize());
	}
	
	public String rending(String template, Data data) {
		Template t = gt.getTemplate(template);
		t.binding("data", data);
		return t.render();
	}
}