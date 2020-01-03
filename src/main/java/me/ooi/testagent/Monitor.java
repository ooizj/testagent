package me.ooi.testagent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author jun.zhao
 * @since 1.0
 */
public class Monitor {
	
	public static final Monitor INSTANCE = new Monitor() ;  
	
	private Monitor(){
	}
	
	private Queue<MethodInvokeTakingTime> takingTimeQueue = new LinkedList<MethodInvokeTakingTime>() ; 
	
	public void put(String methodId, String className, String methodName, long startTime, long endTime){
//		XLog.info(null, String.format("方法[%s]耗时[%s]", methodId, Long.toString(endTime-startTime)));
		takingTimeQueue.offer(new MethodInvokeTakingTime(methodId, endTime-startTime, startTime, endTime)) ; 
	}
	
	public List<MethodInvokeTakingTime> subData(long startTime, long endTime){
		List<MethodInvokeTakingTime> ret = new ArrayList<MethodInvokeTakingTime>() ; 
		for (MethodInvokeTakingTime t : takingTimeQueue) {
			if( t.getStartTime() >= startTime && t.getEndTime() <= endTime ){
				ret.add(t) ; 
			}
		}
		return ret ; 
	}
	
	public List<MethodInvokeTakingTime> sortData(List<MethodInvokeTakingTime> list){
		Collections.sort(list, new Comparator<MethodInvokeTakingTime>() {
			@Override
			public int compare(MethodInvokeTakingTime o1, MethodInvokeTakingTime o2) {
				return (int) (o2.getTakingTime() - o1.getTakingTime()) ;
			}
		});
		return list ; 
	}

}
