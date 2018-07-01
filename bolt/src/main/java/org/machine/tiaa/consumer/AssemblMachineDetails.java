package org.machine.tiaa.consumer;

public class AssemblMachineDetails {

	private int numberOfAssemberedMachine =0;
	private int numberOfTotalTime = 0;
	public int getNumberOfAssemberedMachine() {
		return numberOfAssemberedMachine;
	}
	public void setNumberOfAssemberedMachine(int numberOfAssemberedMachine) {
		this.numberOfAssemberedMachine = numberOfAssemberedMachine;
	}
	public int getNumberOfTotalTime() {
		return numberOfTotalTime;
	}
	public void setNumberOfTotalTime(int numberOfTotalTime) {
		this.numberOfTotalTime = numberOfTotalTime;
	}
	@Override
	public String toString() {
		return "AssemblMachineDetails [numberOfAssemberedMachine=" + numberOfAssemberedMachine + ", numberOfTotalTime="
				+ numberOfTotalTime + "]";
	}
	
	
}
