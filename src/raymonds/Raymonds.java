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
	
	public LinkedList<Process> requestQueue = new LinkedList<Process>(); 
	
	public void assignToken(Process p) {
		if ( (holderEnum == HolderEnum.Self) && (!usingResource) && (!requestQueue.isEmpty()) ) {
			holderProc = requestQueue.pop() ;
			if (p.getProcessID() == holderProc.getProcessID()) {
				holderEnum = HolderEnum.Self;
			} else {
				holderEnum = HolderEnum.Neighbor;
			}
			
			asked = false;
			
			if (holderEnum == HolderEnum.Self) {
				usingResource = true;
			} else {
				assignToken(holderProc); // Check this, supposed to be "send token to holder"
			}
		}
	}
	
	public void sendRequest(Process p) {
		if ( (holderEnum == HolderEnum.Self) && (!requestQueue.isEmpty()) && (!asked) ) {
			sendRequest(holderProc);
			asked = true;
		}
	}
	
	public void requestResource(Process p) {
		requestQueue.push(p);
		assignToken(p);
		sendRequest(p);
	}

}
