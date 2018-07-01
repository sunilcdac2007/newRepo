package org.machine.tiaa.consumer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Consumer implements Callable<AssemblMachineDetails> {

	protected BlockingQueue machinQueue = null;
	protected BlockingQueue boltQueue = null;
	protected int numberOfAssemberMachine=0;
	private int timeRequiredToAssembel = 0;
	protected AssemblMachineDetails finalMachinDetails ;
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private ConcurrentMap<String, Integer> map;
	
	
	public Consumer(BlockingQueue machinQueue, BlockingQueue boltQueue, AssemblMachineDetails finalMachinDetails , int timeRequiredToAssembel,ConcurrentMap<String, Integer> map ) {
		super();
		this.machinQueue = machinQueue;
		this.boltQueue = boltQueue;
		this.finalMachinDetails = finalMachinDetails;
		this.timeRequiredToAssembel = timeRequiredToAssembel;
		this.finalMachinDetails = finalMachinDetails;
		this.map = map;
	}

	public AssemblMachineDetails call() throws Exception {
		// System.out.println(Thread.currentThread().getName());
		while(machinQueue.size() > 0 && boltQueue.size() > 1){
			// System.out.println(machinQueue.size()+": "+boltQueue.size());
			
			if(boltQueue.size() > 1)
			{
			readWriteLock.readLock().lock();
			String bolt1 = (String)boltQueue.take();
			String bolt2 = (String)boltQueue.take();
			String MachineId = (String)machinQueue.take();
			
			int numOfReadyMachin = finalMachinDetails.getNumberOfAssemberedMachine();
			int totalTime = finalMachinDetails.getNumberOfTotalTime();
			readWriteLock.readLock().unlock();
			
			readWriteLock.writeLock().lock();
			// System.out.println(Thread.currentThread().getName());
			if(map.containsKey(Thread.currentThread().getName())){
				// System.out.println("already exsting thread");
				int val = map.get(Thread.currentThread().getName());
				int min = val;
				for(String mapSet : map.keySet()){
					int temp = map.get(mapSet);
					if( temp < min)
						min = map.get(mapSet); 
				}
				map.put(Thread.currentThread().getName(), map.get(Thread.currentThread().getName())+1);
				if(min == val){
					finalMachinDetails.setNumberOfTotalTime(finalMachinDetails.getNumberOfTotalTime() + this.timeRequiredToAssembel );
				}
				
			} else {
				// System.out.println("New thread in map");
				map.put(Thread.currentThread().getName(), 1);
				if(finalMachinDetails.getNumberOfTotalTime() ==0){
					finalMachinDetails.setNumberOfTotalTime(finalMachinDetails.getNumberOfTotalTime() + this.timeRequiredToAssembel );
				}
			}
			finalMachinDetails.setNumberOfAssemberedMachine(finalMachinDetails.getNumberOfAssemberedMachine()+1);
		
			readWriteLock.writeLock().unlock();
			}
		
		}
		
		
		return finalMachinDetails;
	}

}
