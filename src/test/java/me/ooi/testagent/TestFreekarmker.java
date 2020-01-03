package me.ooi.testagent;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class TestFreekarmker {
	
	@Test
	public void t1() throws Exception{
		// Create your Configuration instance, and specify if up to what FreeMarker
		// version (here 2.3.29) do you want to apply the fixes that are not 100%
		// backward-compatible. See the Configuration JavaDoc for details.
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);

		// Specify the source where the template files come from. Here I set a
		// plain directory for it, but non-file-system sources are possible too:
//		cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));
		cfg.setClassForTemplateLoading(this.getClass(), "/");

		// From here we will set the settings recommended for new projects. These
		// aren't the defaults for backward compatibilty.

		// Set the preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear.
		// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
		cfg.setLogTemplateExceptions(false);

		// Wrap unchecked exceptions thrown during template processing into TemplateException-s:
//		cfg.setWrapUncheckedExceptions(true);

		// Do not fall back to higher scopes when reading a null loop variable:
//		cfg.setFallbackOnNullLoopVariable(false);
		
		Template template = cfg.getTemplate("a.html") ;
		Map<String, Object> root = new HashMap<String, Object>() ; 
		root.put("userName", "小明") ; 
		List<String> myList = new ArrayList<String>() ; 
		myList.add("aaa") ; 
		myList.add("222") ; 
		myList.add("ttt") ; 
		root.put("myList", myList) ; 
		
		Writer out = new OutputStreamWriter(System.out);
		template.process(root, out);
		
		cfg.clearTemplateCache();
	}

}
