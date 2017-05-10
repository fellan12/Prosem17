package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class VirtualMachine {
	// TODO: try catch for EvalObj int / bool

	/*
	* Computes one step from the configurations
	*/
	public Set<Configuration> step(Configuration conf){

		if(conf.hasNext()){
			EvalObj eo;
			int a1, a2, n;
			Code c1,c2;
			if(!conf.getState().getExceptionalState()){
				switch(conf.peekInst().opcode){
					case ADD:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getInt();
						// Get rhs
						a2 = conf.getEval().getInt();
						// add sum to stack
						eo = new EvalObj(a1+a2);
						conf.addEval(eo);
						break;
					case AND:
						//consume
						conf.getInst();
						// Get lhs
						boolean b1 = conf.getEval().getBool();
						// Get rhs
						boolean b2 = conf.getEval().getBool();
						// add res to stack
						eo = new EvalObj(b1 && b2);
						conf.addEval(eo);
						break;
					
					case BRANCH:
						Branch b = (Branch) conf.getInst();
						// Pop stack to see tt or ff
						if (conf.getEval().getBool()) {
							conf.addCode(b.c1);
						} else {
							conf.addCode(b.c2);
						}
						break;
					case EQ:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getInt();
						// Get rhs
						a2 = conf.getEval().getInt();
						// add res to stack
						eo = new EvalObj(a1 == a2);
						conf.addEval(eo);
						break;
					case FALSE:
						//consume
						conf.getInst();
						// Add false to evalstack
						eo = new EvalObj(false);
						conf.addEval(eo);
						break;
					case FETCH:
						Fetch f = (Fetch) conf.getInst();
						
						try {
							// get value from storage
							n = conf.getStorage().get(f.x);
							// Add value to eval stack
							eo = new EvalObj(n);
							conf.addEval(eo);
							break;
						} catch (NullPointerException e) {
							System.out.println("Variable doesn't exist.");
							System.exit(1);
						}
						
						
					case LE:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getInt();
						// Get rhs
						a2 = conf.getEval().getInt();
						// add res to stack
						eo = new EvalObj(a1 <= a2);
						conf.addEval(eo);
						break;
					case LOOP:
						Loop l = (Loop) conf.getInst();
						// Create branch component
						c1 = new Code();
						c1.addAll(l.c2);
						c1.add(l);
						c2 = new Code();
						c2.add(new Noop());
						Branch br = new Branch(c1, c2);
						// Add code to code stack
						Code c3 = new Code();
						c3.add(br);
						conf.addCode(c3);
						conf.addCode(l.c1);
						break;
					case MULT:
						//consume
						conf.getInst(); 
						// Get lhs
						a1 = conf.getEval().getInt();
						// Get rhs
						a2 = conf.getEval().getInt();
						// add product to stack
						eo = new EvalObj(a1*a2);
						conf.addEval(eo);
						break;
					
					case NEG:
						//consume
						conf.getInst();
						// If true, add false to stack, otherwise add true.
						if (conf.getEval().getBool()) {
							eo = new EvalObj(false);
						} else {
							eo = new EvalObj(true);
						}
						conf.addEval(eo);
						break;
					case NOOP:
						//consume
						conf.getInst();
						// Do nothing.
						break;
					case PUSH:
						Push p = (Push) conf.getInst();
						// Add int to evalstack
						eo = new EvalObj(p.getValue());
						conf.addEval(eo);
						break;
					case STORE:
						Store s = (Store) conf.getInst();
						// Get value from eval stack
						n = conf.getEval().getInt();
						// Get variable from store instruction
						String x = s.x;
						// Add mapping to storage
						conf.addStorage(x, n);
						break;
					case SUB:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getInt();
						// Get rhs
						a2 = conf.getEval().getInt();
						// add diff to stack
						eo = new EvalObj(a1-a2);
						conf.addEval(eo);
						break;
					
					case TRUE:
						//consume
						conf.getInst();
						// Add true to evalstack
						eo = new EvalObj(true);
						conf.addEval(eo);
						break;

					case DIV:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getInt();
						// Get rhs
						a2 = conf.getEval().getInt();
						if(a2 != 0){
							eo = new EvalObj(a1/a2);
							conf.addEval(eo);
						}else{
							conf.getState().setExceptionalState(true);
						}
						break;

					case TRYC:
						TryC tc = (TryC) conf.getInst();
						c1 = tc.c1;
						Catch cat = new Catch(tc.c2);
						c1.add(cat);
						conf.addCode(c1);
						break;
					case CATCH:
						conf.getInst();
				}
				
			}else{	
				//CATCH
				if(conf.peekInst().opcode == Inst.Opcode.CATCH){
					Catch cat = (Catch) conf.getInst();
					conf.addCode(cat.c2);
					conf.getState().setExceptionalState(false);
				}
				else {
					conf.getInst();
				}
			}
			
		}
		
		conf.increaseStepCount();
		return conf;
	}
}
