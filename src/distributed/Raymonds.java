package distributed;

import java.io.IOException;
import raymonds.Process;

public class Raymonds {
	
	public static void main(String[] args) throws IOException {
			
	}
	
	public static void assignToken(Process p, Process holderProc) {
		if ( (p.holderEnum == Process.HolderEnum.Self) && (!p.usingResource) && (!p.requestQueue.isEmpty()) ) {
			holderProc = p.requestQueue.pop() ;
			
			if (p.getProcessID() == holderProc.getProcessID()) { //i.e. the process p is at the front of its own queue
				p.holderEnum = Process.HolderEnum.Self;
			} else {
				p.holderEnum = Process.HolderEnum.Neighbor;
				holderProc.holderEnum = Process.HolderEnum.Self ;
			}
			
			p.asked = false;
			
			if (p.holderEnum == Process.HolderEnum.Self) {
				p.usingResource = true;
			} else {
				assignToken(p, holderProc); // Check this, supposed to be "send token to holder"
			}
		}
	}
	
	public static void sendRequest(Process p, Process holderProc) {
		if ( (p.holderEnum != Process.HolderEnum.Self) && (!p.requestQueue.isEmpty()) && (!p.asked) ) {
			sendRequest(p, holderProc);
			p.asked = true;
		}
	}
	
	public static void requestResource(Process p, Process holderProc) {
		p.requestQueue.push(p);
		assignToken(p, holderProc);
		sendRequest(p, holderProc);
	}
	
	public static void releaseResource(Process p, Process holderProc) {
		p.usingResource = false;
		assignToken(p, holderProc);
		sendRequest(p, holderProc);
	}
	
	public static void receivedRequestFromNeighbor(Process p, Process holderProc, Process neighbor) {
		p.requestQueue.push(neighbor);
		assignToken(p, holderProc);
		sendRequest(p, holderProc);
	}

	public static void receivedToken(Process p, Process holderProc) {
		p.holderEnum = Process.HolderEnum.Self ;
		holderProc = p;
		assignToken(p, holderProc);
		sendRequest(p, holderProc);	
	}
	
}
