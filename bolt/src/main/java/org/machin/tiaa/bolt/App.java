package org.machin.tiaa.bolt;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        BlockingQueue machinQueue = new ArrayBlockingQueue(1024);
        try {
			machinQueue.put("test");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        System.out.println(machinQueue.size());
        
    }
}
