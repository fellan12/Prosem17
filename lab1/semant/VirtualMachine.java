package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class VirtualMachine {

	/*
	* Computes one step from the configurations
	*/
	public Configuration step(Configuration conf){

		if(conf.hasNext()){
			switch(conf.getInst().opcode){
				case ADD: 

				case AND:
				
				case BRANCH:
				
				case EQ:
				
				case FALSE:
				
				case FETCH: 
				
				case LE:
				
				case LOOP:
				
				case MULT: 
				
				case NEG:
				
				case NOOP:
				
				case PUSH:
				
				case STORE:
				
				case SUB:
				
				case TRUE:

			}
		}
		
		conf.increaseStepCount();
		return conf;
	}
}