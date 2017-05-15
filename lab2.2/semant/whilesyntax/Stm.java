package semant.whilesyntax;

import semant.WhileVisitor;
import semant.amsyntax.Code;
import semant.signexc.*;
import java.util.*;

public abstract class Stm {
    public abstract Code accept(WhileVisitor v);
    public int controlPoint = -1;
    public boolean unReachable = false;
    public boolean possibleExceptionRaiser = false;
 	public boolean visited = false;
 	public HashMap<String, SignExc> lub = new HashMap<String, SignExc>();

 	public void addLub(HashMap<String, SignExc> val){
 		lub.putAll(val);
 	}

 	public String printLub(){
 		StringBuilder sb = new StringBuilder();
 		sb.append("{");
 		for (String s : lub.keySet()) {
 			sb.append(s + "=" + lub.get(s) + ",");
 		}
 		String res = sb.toString().substring(0,sb.toString().length()-1);
 		return res + "}";
 	}
}
