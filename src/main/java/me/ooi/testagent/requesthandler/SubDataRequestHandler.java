package me.ooi.testagent.requesthandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.ooi.testagent.MethodInvokeTakingTime;
import me.ooi.testagent.Monitor;
import me.ooi.utils.FreemarkerUtils;
import me.ooi.utils.ResponseUtils;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class SubDataRequestHandler implements CmdRequestHandler {

	@Override
	public void handle(Writter writter, Map<String, String> params) {
		long startTime = Long.parseLong(params.get("startTime")) ; 
		long endTime = Long.parseLong(params.get("endTime")) ;
		List<MethodInvokeTakingTime> list = Monitor.INSTANCE.subData(startTime, endTime) ; 
		list = Monitor.INSTANCE.sortData(list) ;
		
		Map<String, Object> root = new HashMap<String, Object>() ; 
		root.put("list", list) ; 
		String html = FreemarkerUtils.INSTANCE.getText("subData.html", root) ; 
		byte[] data = html.getBytes() ; 
		try {
			ResponseUtils.writeResponseHead(writter, "text/html; charset=utf-8", data.length);
			writter.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
