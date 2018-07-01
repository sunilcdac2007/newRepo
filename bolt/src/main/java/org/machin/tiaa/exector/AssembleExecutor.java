package org.machin.tiaa.exector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;

import org.machine.tiaa.consumer.AssemblMachineDetails;
import org.machine.tiaa.consumer.Consumer;
import org.machine.tiaa.producer.Producer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

public class AssembleExecutor {

	protected BlockingQueue machinQueue = new ArrayBlockingQueue(1024);
	protected BlockingQueue boltQueue = new ArrayBlockingQueue(1024);
	protected AssemblMachineDetails finalMachinDetails;
	int timeToAssemblePerMachine = 1;
	
	
	public AssembleExecutor(int timeToAssemblePerMachine) {
		super();
		this.timeToAssemblePerMachine = timeToAssemblePerMachine;
	}


	public static void main(String args[]){;
		AssembleExecutor asm = new AssembleExecutor(60);
		AssemblMachineDetails obj = asm.jobExecuter(6,12,60,3);
		System.out.println(obj.toString());
	}
	
	
	AssemblMachineDetails jobExecuter(int numerOfMachin, int numOfBolt, int assembleTimePerMachine,int numberOfEmployee){
		AssemblMachineDetails numOfmachinDelivered=null;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfEmployee+1);
		List<Future<AssemblMachineDetails>> list = new ArrayList<Future<AssemblMachineDetails>>();
		ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
		AssemblMachineDetails finalMachinDetails = new AssemblMachineDetails();
		Future producerStatus = executorService.submit(new Producer(machinQueue,boltQueue,numerOfMachin,numOfBolt));	
		for(int i=0;i<numberOfEmployee;i++ ){
			Future<AssemblMachineDetails> future = executorService.submit(new Consumer(machinQueue,boltQueue,finalMachinDetails,timeToAssemblePerMachine,map));
			list.add(future);
		}		
	    for(Future<AssemblMachineDetails> fut : list){
            try {
            	// System.out.println(fut);
            	AssemblMachineDetails status = fut.get();
            	// System.out.println(status.toString());
               // System.out.println(new Date()+ "::"+fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }	    
	    executorService.shutdown();		
		try {
			for(Future<AssemblMachineDetails> details: list)
			//	System.out.println(details.toString());
			numOfmachinDelivered= list.get(list.size()-1).get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numOfmachinDelivered;
	}

}
