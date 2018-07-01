package org.machin.tiaa.bolt;

import static org.junit.Assert.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConsumerTest {
	protected BlockingQueue machinQueue = new ArrayBlockingQueue(1024);
	protected BlockingQueue boltQueue = new ArrayBlockingQueue(1024);;
		
	@Test
	public void testSequentialEnqueueDeq(){
		
		try {
			Callable< String> t1 = new Callable<String>() {

				@Override
				public String call() throws Exception {
					// TODO Auto-generated method stub
					return "test";
				}
			};
			machinQueue.put(t1);
			
			boltQueue.put(t1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(machinQueue.size(),1);
		assertEquals(boltQueue.size(),1);
		try {
			machinQueue.take();
			boltQueue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(machinQueue.size(),0);
		assertEquals(boltQueue.size(),0);
	}
	
	
}
