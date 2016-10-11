package raymonds;

import java.util.LinkedList;


public class Raymonds {
	
	public Process holderProc;
	
	public void assignToken(Process p) {
		if ( (p.holderEnum == Process.HolderEnum.Self) && (!p.usingResource) && (!p.requestQueue.isEmpty()) ) {
			//holderProc = p.requestQueue.pop() ;
			p.requestQueue.pop().holderEnum = Process.HolderEnum.Self ;
			/*
			if (p.getProcessID() == holderProc.getProcessID()) {
				p.holderEnum = Process.HolderEnum.Self;
			} else {
				p.holderEnum = Process.HolderEnum.Neighbor;
			}
			*/
			
			p.asked = false;
			
			if (p.holderEnum == Process.HolderEnum.Self) {
				p.usingResource = true;
			} else {
				assignToken(holderProc); // Check this, supposed to be "send token to holder"
			}
		}
	}
	
	public void sendRequest(Process p) {
		if ( (p.holderEnum != Process.HolderEnum.Self) && (!p.requestQueue.isEmpty()) && (!p.asked) ) {
			sendRequest(holderProc);
			p.asked = true;
		}
	}
	
	public void requestResource(Process p) {
		p.requestQueue.push(p);
		assignToken(p);
		sendRequest(p);
	}
	
	public void releaseResource(Process p) {
		p.usingResource = false;
		assignToken(p);
		sendRequest(p);
	}
	
	public void receivedRequestFromNeighbor(Process p, Process neighbor) {
		p.requestQueue.push(neighbor);
		assignToken(p);
		sendRequest(p);
	}

	public void receivedToken(Process p) {
		p.holderEnum = Process.HolderEnum.Self ;
		holderProc = p;
		assignToken(p);
		sendRequest(p);		
	}
	
}
