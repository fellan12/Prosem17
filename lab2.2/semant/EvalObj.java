package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import semant.signexc.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class EvalObj {
	private SignExc intVal;
	private TTExc boolVal;
	private boolean intSet = false;
	private boolean boolSet = false;

	public EvalObj(SignExc n) {
		intVal = n;
		intSet = true;
	}

	public EvalObj(TTExc b) {
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

	public boolean equals(Object o) {
		if (!(o instanceof EvalObj))
			return false;
		EvalObj oc = (EvalObj) o;
		return oc.intVal == intVal && oc.boolVal == boolVal;
	}

}