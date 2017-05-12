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

    public int hashCode(){
    	return storage.size();
    }

    public boolean equal(State s) {

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