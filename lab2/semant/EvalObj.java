package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class EvalObj {
	private SignExc intVal;
	private TTExc boolVal;
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

	public SignExc getSign() {
		return intVal;
	}

	public TTExc getTT() {
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