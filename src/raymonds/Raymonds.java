package raymonds;

import java.util.LinkedList;


public class Raymonds {
	
	public boolean usingResource;
	public boolean asked;
	public enum HolderEnum {
		Self,
		Neighbor;
	}
	public HolderEnum holderEnum;
	public Process holderProc;
	
	private LinkedList<Process> readyQueue = new LinkedList<Process>(); 
	
	public void assignToken(Process p) {
		if ( (holderEnum == HolderEnum.Self) && (!usingResource) && (!readyQueue.isEmpty()) ) {
			holderProc = readyQueue.pop() ;
			if (p.getProcessID() == holderProc.getProcessID()) {
				holderEnum = HolderEnum.Self;
			} else {
				holderEnum = HolderEnum.Neighbor;
			}
			
			asked = false;
			
			if (holderEnum == HolderEnum.Self) {
				usingResource = true;
			} else {
				assignToken(holderProc); // Check this, supposed to be "send token to user"
			}
		}
	}
	
	public void sendRequest(Process p) {
		if ( (holderEnum == HolderEnum.Self) && (!readyQueue.isEmpty()) && (!asked) ) {
			sendRequest(holderProc);
			asked = true;
		}
	}

}
