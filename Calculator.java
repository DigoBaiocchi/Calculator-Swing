import java.util.ArrayList;

/**
 * Program Name:Calculator.java
 * Purpose: This class holds the logic for all the calculations for
 * 					regular arithmetic and binary operations
 * @Author: Rodrigo Baiocchi Ferreira 1288669
 * @Date: Aug 08, 2025
 */

public class Calculator {
	// Implements the accumulator, current operator, register as private to class
	private long accumulator, register;
	private String currentOperator;
	
	/**
	* Constructs a new Calculator object and set isAccumulatorEmpty flag to true
	*/
	public Calculator() {
		currentOperator = "";
		System.out.println("currentOperator is empty: " + currentOperator.isEmpty());
	}
	
	// Implements all math functions
	// Implements the regular arithmetic operators (+, -, *, /, mod) 
	public boolean calculateResult() {
		boolean divisionByZeroError = false;
		if (currentOperator.equals("*"))
			setAccumulator(accumulator * register);
		else if (currentOperator.equals("/")) {
			try {
				setAccumulator(accumulator / register);
			}
			catch (ArithmeticException er) {
				setCurrentOperator("");
				System.out.println(er);
				System.out.println("Cannot divide by zero");
				return divisionByZeroError = true;
			}
		}
		else if (currentOperator.equals("+"))
			setAccumulator(accumulator + register);
		else if (currentOperator.equals("-"))
			setAccumulator(accumulator - register);
		else if (currentOperator.equals("mod"))
			setAccumulator(accumulator % register);
		else if (currentOperator.equals("and"))
			setAccumulator(accumulator & register);
		else if (currentOperator.equals("nand"))
			setAccumulator(~(accumulator & register));
		else if (currentOperator.equals("or"))
			setAccumulator(accumulator | register);
		else if (currentOperator.equals("nor"))
			setAccumulator(~(accumulator | register));
		else if (currentOperator.equals("xor"))
			setAccumulator(accumulator ^ register);
		else if (currentOperator.equals("<<"))
			setAccumulator(accumulator << register);
		else if (currentOperator.equals(">>"))
			setAccumulator(accumulator >> register);
		if (currentOperator.equals("not"))
			setAccumulator(~accumulator);
		else
			System.out.println("No Calculations were made.");
		setCurrentOperator("");
		
		return divisionByZeroError;
	}
	/**
	* Gets the accumulator of this object
	* @return the accumulator
	*/
	public long getAccumulator() {
		return accumulator;
	}

	/**
	* Sets the accumulator of this object
	* @param accumulator
	*/
	public void setAccumulator(long accumulator) {
		this.accumulator = accumulator;
	}

	/**
	* Gets the register of this object
	* @return the register
	*/
	public long getRegister() {
		return register;
	}

	/**
	* Sets the register of this object
	* @param register
	*/
	public void setRegister(long register) {
		this.register = register;
	}

	/**
	* Gets the currentOperator of this object
	* @return the currentOperator
	*/
	public String getCurrentOperator() {
		return currentOperator;
	}

	/**
	* Sets the currentOperator of this object
	* @param currentOperator
	*/
	public void setCurrentOperator(String currentOperator) {
		this.currentOperator = currentOperator;
	}
}
//end class