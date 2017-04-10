package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class Configuration {
	private Code c = new Code();
	private Stack e = new Stack();
	private ArrayList s = new ArrayList();
	private int stepCount = 1;
	private boolean complete = false;

	/*
	* Constructor for the Configurations class
	*/
	public Configuration(Code c){
		this.c = c;
	}

	public int getStepCount(){
		return stepCount;
	}

	public void increaseStepCount(){
		if(hasNext()){
			stepCount++;
			if(getCode().isEmpty()){
				complete = true;
			}
		}
	}

	/*
	* Get Code list
	*/
	public Code getCode(){
		return c;
	}

	/*
	* Get the top instruction in the code list
	*/
	public Inst getInst(){
		Inst ins = c.get(0);
		c.remove(0);
		return ins;
	}

	/*
	* Get the evaluation stack
	*/
	public Stack getEval(){
		return e;
	}

	/*
	* Get the storage list
	*/
	public ArrayList getStorage(){
		return s;
	}

	/*
	* Is there conputations left to do?
	* Return: True or False
	*/
	public boolean hasNext(){
		return !complete;
	}

	/*
	* Prints out configuration and step count
	*/
	public void printConfig(){
		String codeString = "ε";
		String evalString = "ε";
		String storageString = "ε";
		if(!c.isEmpty()){
			codeString = "";
			for (Inst i : c) {
				codeString += i+":";
			}
			codeString = codeString.substring(0, codeString.length()-1);
		}
		System.out.println("Step: " + stepCount +"\n");

		if(!e.isEmpty()){
			evalString = e.toString().replaceAll("\\[", "").replaceAll("]", "");
		}
		if(c.isEmpty()){
			System.out.println("Code: " + codeString + " (Empty)\n");
		}else{
			System.out.println("Code: " + codeString +"\n");
		}

		if(e.isEmpty()){
			System.out.println("Evalutation Stack: " +evalString + " (Empty)\n");
		}else{
			System.out.println("Evalutation Stack: " +evalString+"\n");
		}
		if(s.isEmpty()){
			System.out.println("Storage: " +storageString + " (Empty)\n");
		}else{
			System.out.println("Storage: " +storageString+"\n");
		}

		System.out.println("<" + codeString + ", " + evalString + ", " + storageString + ">"+"\n");
	}
}