package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import semant.signexc.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class State implements Cloneable{
	boolean exceptional = false;
	HashMap <String, SignExc> storage = new HashMap<String, SignExc>();

	public State(){}

	public State(State s){
		this.exceptional = s.getExceptionalState();
		this.storage = (HashMap) s.getMappings().clone();
	}

 	public boolean getExceptionalState(){
		return exceptional;
	}

	public void setExceptionalState(boolean b){
		exceptional = b;
	}

	public HashMap <String, SignExc> getMappings() {
		return storage;
	}

	public String printState(){
		String storageString = "";
		if(!getMappings().isEmpty()){
			for (String key : getMappings().keySet()) {
				storageString += key + "=" + getMappings().get(key) +":";
			}
			storageString = storageString.substring(0, storageString.length()-1);
		}
		return storageString;
	}

	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int shashCode(){
    	int hashcode = 0;
    	int weight = 1;
    	for (String s : storage.keySet()) {
    		switch(storage.get(s)){
    			case NONE_A: 
    				hashcode += 1*weight;
    				break;
    			case NEG: 
    				hashcode += 2*weight;
    				break;
    			case ZERO: 
    				hashcode += 3*weight;
    				break;
    			case POS: 
    				hashcode += 4*weight;
    				break;
    			case ERR_A: 
    				hashcode += 5*weight;
    				break;
    			case NON_POS: 
    				hashcode += 6*weight;
    				break;
    			case NON_ZERO: 
    				hashcode += 7*weight;
    				break;
    			case NON_NEG: 
    				hashcode += 8*weight;
    				break;
    			case Z: 
    				hashcode += 9*weight;
    				break;
    			case ANY_A: 
    				hashcode += 10*weight;
    				break;
    		}
    		weight *= 10;
    	}
    	return hashcode;
    }

    public boolean equal(Object o) {
    	State s = (State) o;
	    try{
	        for (String k : storage.keySet())
	        {
	            if (!s.getMappings().get(k).equals(storage.get(k))) {
	                return false;
	            }
	        } 
	        for (String y : s.getMappings().keySet())
	        {
	            if (!storage.containsKey(y)) {
	                return false;
	            }
	        } 
	    } catch (NullPointerException np) {
	        return false;
	    }
	    return true && exceptional == s.getExceptionalState();
	}
}