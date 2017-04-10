package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class EvalObj {
	private int intVal;
	private boolean boolVal;
	private boolean intSet = false;
	private boolean boolSet = false;

	public EvalObj(int n) {
		intVal = n;
		intSet = true;
	}

	public EvalObj(boolean b) {
		boolVal = b;
		boolSet = true;
	}

	public int getInt() {
		return intVal;
	}

	public boolean getBool() {
		return boolVal;
	}

	public String printObj(){
		String printString = "";
		if(intSet){
			printString =""+intVal;
		}else if(boolSet){
			printString = ""+boolVal;
		}
		return printString;
	}

}