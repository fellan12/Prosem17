package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class State {
	boolean exceptional = false;
	HashMap <String, Integer> storage = new HashMap<String, Integer>();

	public boolean getExceptionalState(){
		return exceptional;
	}

	public void setExceptionalState(boolean b){
		exceptional = b;
	}

	public HashMap <String, Integer> getMappings() {
		return storage;
	}
}