package raymonds;

public class Process {

	private String processID;
	
	private ProcessState processState;
	public enum ProcessState {
		NEW,
		READY,
		BLOCKED,
		RUNNING,
		FINISHED;
	}

	public Process(String processID ) {
		super();
		this.processID = processID;
		this.processState = ProcessState.NEW;
		
	}

	/**
	 * @return the processID
	 */
	public String getProcessID() {
		return processID;
	}
	
	/**
	 * @return the processState
	 */
	public ProcessState getProcessState() {
		return processState;
	}
	
	/**
	 * @param processState the processState to set
	 */
	public void setProcessState(ProcessState processState) {
		this.processState = processState;
	}
	
    
}