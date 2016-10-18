package raymonds;

import java.util.LinkedList;
import java.util.ArrayList;
public class Process {

	public Process (String processID, HolderEnum holderEnum, boolean usingResource, boolean asked) {
		this.processID = processID;
		this.holderEnum = holderEnum;
		this.usingResource = usingResource;
		this.asked = asked;
		this.requestQueue = new LinkedList<Process>();
		this.processState = Process.ProcessState.NEW ;
	}
	
	public Process(String processID ) {
		super();
		this.processID = processID;
		this.processState = ProcessState.NEW;
		
	}
	
	private String processID;
	public enum HolderEnum {
		Self,
		Neighbor;
	}
	public HolderEnum holderEnum;
	
	public boolean usingResource = false;
	public boolean asked = false;
	public ArrayList<Process> neighbors = new ArrayList<Process>();

	private ProcessState processState;
	public enum ProcessState {
		NEW,
		READY,
		BLOCKED,
		RUNNING,
		FINISHED;
	}
	
	public LinkedList<Process> requestQueue = new LinkedList<Process>(); 

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
	
	/**
	 * @param Process that is being added to the neighbor array
	 */
	public void addNeighbor(Process p)	{
		this.neighbors.add(p);
	}
	
	/**
	 * @return ArrayList of all the neighbors
	 */
	public ArrayList<Process> getNeighbors()
	{
		return this.neighbors;
	}
	
    
}