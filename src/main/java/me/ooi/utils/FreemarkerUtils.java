package me.ooi.utils;

import java.io.StringWriter;
import java.io.Writer;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class FreemarkerUtils {
	
	public static final FreemarkerUtils INSTANCE = new FreemarkerUtils() ; 
	
	private Configuration cfg ; 
	
	private FreemarkerUtils() {
		init();
	}
	
	private void init(){
		cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setClassForTemplateLoading(this.getClass(), "/template");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
	}
	
	public void write(Writer out, String templateName, Object dataModel) {
		try {
			Template template = cfg.getTemplate(templateName) ;
			template.process(dataModel, out);
		} catch (Exception e) {
			throw new FreemarkerProcessException(e) ; 
		}
	}
	
	public String getText(String templateName, Object dataModel) {
		StringWriter sw = new StringWriter() ; 
		write(sw, templateName, dataModel) ; 
		return sw.toString() ; 
	}

}
