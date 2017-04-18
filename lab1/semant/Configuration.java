package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class Configuration {
	private Code c = new Code();
	private Stack<EvalObj> e = new Stack<EvalObj>();
	private State s = new State();
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
	public void addCode(Code cd){
		c.addAll(0,cd);
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
	*
	*/
	public State getState(){
		return s;
	}

	/*
	* Get the top instruction in the code list
	*/
	public Inst peekInst(){
		return c.get(0);
	}

	/*
	* Get the evaluation stack
	*/
	public EvalObj getEval(){
		return e.pop();
	}

	public void addEval(EvalObj x){
		e.push(x);
	}

	/*
	* Get the storage list
	*/
	public HashMap<String,Integer> getStorage() {
		return s.getMappings();
	}

	public void addStorage(String x, int n) {
		s.getMappings().put(x,n);
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
	
		System.out.println("Step: " + stepCount +"\n");

		if(!c.isEmpty()){
			codeString = "";
			for (Inst i : c) {
				codeString += i+":";
			}
			codeString = codeString.substring(0, codeString.length()-1);
		}

		if(!e.isEmpty()){
			evalString = "";
			for (EvalObj eo: e) {
				evalString = eo.printObj() + ":" + evalString;
			}
			evalString = evalString.substring(0, evalString.length()-1);			
		}

		if(!s.getMappings().isEmpty()){
			storageString = "";
			for (String key : s.getMappings().keySet()) {
				storageString += key + "=" + s.getMappings().get(key) +":";
			}
			storageString = storageString.substring(0, storageString.length()-1);			
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
		if(s.getMappings().isEmpty()){
			System.out.println("Storage: " +storageString + " (Empty)\n");
		}else{
			System.out.println("Storage: " +storageString+"\n");
		}

		System.out.println("<" + codeString + ", " + evalString + ", " + storageString + ">"+"\n");
	}
}