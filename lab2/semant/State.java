package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import semant.signexc.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class State {
	boolean exceptional = false;
	HashMap <String, SignExc> storage = new HashMap<String, SignExc>();

	public boolean getExceptionalState(){
		return exceptional;
	}

	public void setExceptionalState(boolean b){
		exceptional = b;
	}

	public HashMap <String, SignExc> getMappings() {
		return storage;
	}
}