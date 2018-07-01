package org.machine.tiaa.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class Producer implements Callable<String> {
	
	protected BlockingQueue machinQueue = null;
	protected BlockingQueue boltQueue = null;
	private int numOfMachin = 0;
	private int numOfBolt = 0;
	
	

	public Producer(BlockingQueue machinQueue, BlockingQueue boltQueue, int numOfMachin, int numOfBolt) {
		super();
		this.machinQueue = machinQueue;
		this.boltQueue = boltQueue;
		this.numOfMachin = numOfMachin;
		this.numOfBolt = numOfBolt;
	}

	public String call() throws Exception {
		
		System.out.println(Thread.currentThread().getName());
		for(int i=0; i < numOfBolt ; i++){
			try {
		if(boltQueue.size() < numOfBolt)
			boltQueue.put("Bolt "+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		for(int i=0 ; i< numOfMachin;i++){
			try{
		if(machinQueue.size() < numOfMachin )		
			machinQueue.put("Machine "+i); } catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
		
		return "queue intialized";
	}

}
