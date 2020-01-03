package me.ooi.testagent;

import java.lang.instrument.Instrumentation;

import me.ooi.testagent.requesthandler.NIOCmdServer;
import me.ooi.utils.XLog;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class MonitorAgent {
	
	private static final String TAG = "MonitorAgent" ; 

	public static void premain(String agentOps, Instrumentation inst) {
		XLog.info(TAG, "监视器启动，启动参数["+agentOps+"]");
		inst.addTransformer(new MonitorTransformer("\\$", "^com\\.ibm\\.", "\\$", "."));
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				//http://localhost:9998/?a=tt
				int pacPort = 9998 ; 
				XLog.info(TAG, "启动cmd服务：port["+pacPort+"]");
				new NIOCmdServer(pacPort).start();
			}
		}).start();
		
		XLog.info(TAG, "监视器启动成功。");
	}

}