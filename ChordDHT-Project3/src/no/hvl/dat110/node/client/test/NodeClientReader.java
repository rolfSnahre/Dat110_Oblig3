package no.hvl.dat110.node.client.test;

import java.rmi.AccessException;

/**
 * exercise/demo purpose in dat110
 * @author tdoy
 *
 */

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;

import no.hvl.dat110.file.FileManager;
import no.hvl.dat110.node.Message;
import no.hvl.dat110.rpc.StaticTracker;
import no.hvl.dat110.rpc.interfaces.ChordNodeInterface;
import no.hvl.dat110.util.Hash;
import no.hvl.dat110.util.Util;

public class NodeClientReader extends Thread {

	private boolean succeed = false;
	
	private String filename;
	
	public NodeClientReader(String filename) {
		this.filename = filename;
	}
	
	public void run() {
		sendRequest();
	}
	
	private void sendRequest() {
		
		// Lookup(key) - Use this class as a client that is requesting for a new file and needs the identifier and IP of the node where the file is located
		// assume you have a list of nodes in the tracker class and select one randomly. We can use the Tracker class for this purpose
		
		// connect to an active chord node - can use the process defined in StaticTracker 
		Registry registry = Util.tryIPs();
		
		// Compute the hash of the node's IP address
		String hashIp = Hash.hashOf(Util.activeIP).toString();
		
		// use the hash to retrieve the ChordNodeInterface remote object from the registry
		try {
			ChordNodeInterface node = (ChordNodeInterface) registry.lookup(hashIp);
			// do: FileManager fm = new FileManager(ChordNodeInterface, StaticTracker.N);
			FileManager fm = new FileManager(node, StaticTracker.N);
			// do: boolean succeed = fm.requestToReadFileFromAnyActiveNode(filename);
			succeed = fm.requestToReadFileFromAnyActiveNode(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	
	}
	
	public boolean isSucceed() {
		return succeed;
	}

}
