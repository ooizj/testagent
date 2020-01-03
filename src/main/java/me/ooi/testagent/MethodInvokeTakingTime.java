package me.ooi.testagent;
/**
 * @author jun.zhao
 * @since 1.0
 */
public class MethodInvokeTakingTime {
	
	private String methodId; 
	private long takingTime ;
	private long startTime ; 
	private long endTime ; 
	
	public MethodInvokeTakingTime(String methodId, long takingTime, long startTime, long endTime) {
		super();
		this.methodId = methodId;
		this.takingTime = takingTime;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	public long getTakingTime() {
		return takingTime;
	}
	public void setTakingTime(long takingTime) {
		this.takingTime = takingTime;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	} 

}
