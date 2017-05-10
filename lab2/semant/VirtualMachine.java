package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import semant.signexc.*;
import semant.signexc.SignExcOps;
import java.util.Stack;
import java.util.ArrayList;
import java.util.*;

class VirtualMachine {
	//TODO HANTERA FÖR ANY KONCEPT					

	/*
	* Computes one step from the configurations
	*/
	public Set<Configuration> step(Configuration conf){
		Set<Configuration> confs = new HashSet<Configuration>();
		if(conf.hasNext()){
			EvalObj eo;
			SignExc a1, a2, n;
			Code c1,c2;
			if(!conf.getState().getExceptionalState()){
				SignExcOps ops = new SignExcOps();
				switch(conf.peekInst().opcode){
					case ADD:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add sum to stack
						if(ops.isInt(a1) && ops.isInt(a2)){
							eo = new EvalObj(ops.add(a1,a2));
							conf.addEval(eo);
							break;
						}else{
							System.out.println("a1 or a2 was not Int");
							System.exit(1);
						}


					case SUB:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add diff to stack
						if(ops.isInt(a1) && ops.isInt(a2)){
							eo = new EvalObj(ops.subtract(a1,a2));
							conf.addEval(eo);
							break;
						}else{
							System.out.println("a1 or a2 was not Int");
							System.exit(1);
						}


					case MULT:
						//consume
						conf.getInst(); 
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add product to stack
						if(ops.isInt(a1) && ops.isInt(a2)){
							eo = new EvalObj(ops.multiply(a1,a2));
							conf.addEval(eo);
							break;
						}else{
							System.out.println("a1 or a2 was not Int");
							System.exit(1);
						}


					case DIV:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						if(ops.isInt(a1) && ops.isInt(a2)){
							if(a2 != SignExc.ZERO){
								eo = new EvalObj(ops.divide(a1,a2));
								conf.addEval(eo);
							}else{
								conf.getState().setExceptionalState(true);
							}
							break;
						}else{
							System.out.println("a1 or a2 was not Int");
							System.exit(1);
						}
					

					case AND:
						//consume
						conf.getInst();
						// Get lhs
						TTExc b1 = conf.getEval().getTT();
						// Get rhs
						TTExc b2 = conf.getEval().getTT();
						// add res to stack
						eo = new EvalObj(ops.and(b1,b2));
						conf.addEval(eo);
						break;
					

					case EQ:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add res to stack
						eo = new EvalObj(ops.eq(a1,a2));
						conf.addEval(eo);
						break;


					case LE:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add res to stack
						if(ops.isInt(a1) && ops.isInt(a2)){
							eo = new EvalObj(ops.leq(a1,a2));
							conf.addEval(eo);
							break;
						}else{
							System.out.println("a1 or a2 was not Int");
							System.exit(1);
						}


					case TRUE:
						//consume
						conf.getInst();
						// Add true to evalstack
						eo = new EvalObj(TTExc.TT);
						conf.addEval(eo);
						break;


					case FALSE:
						conf.getInst();
						// Add false to evalstack
						eo = new EvalObj(TTExc.FF);
						conf.addEval(eo);
						break;


					case NEG:
						//consume
						conf.getInst();
						// If true, add false to stack, otherwise add true.
						eo = new EvalObj(ops.neg(conf.getEval().getTT()));
						conf.addEval(eo);
						break;
					

					case FETCH:
						Fetch f = (Fetch) conf.getInst();			
						try {
							// get value from storage
							n = conf.getStorage().get(f.x);
							// Add value to eval stack
							if(ops.isInt(n)){
								eo = new EvalObj(n);
								conf.addEval(eo);
								break;
							}else{
								System.out.println("Fetched value was not an Integer");
								System.exit(1);
							}
							eo = new EvalObj(n);
							conf.addEval(eo);
							break;
						} catch (NullPointerException e) {
							System.out.println("Variable doesn't exist.");
							System.exit(1);
						}


					case PUSH:
						Push p = (Push) conf.getInst();
						// Add int to evalstack
						eo = new EvalObj(ops.abs(p.getValue()));
						conf.addEval(eo);
						break;


					case STORE:
						Store s = (Store) conf.getInst();
						// Get value from eval stack
						n = conf.getEval().getSign();
						// Get variable from store instruction
						String x = s.x;
						// Add mapping to storage
						conf.addStorage(x, n);
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
						// Add branch code to code stack
						Code c3 = new Code();
						c3.add(br);
						conf.addCode(c3);
						conf.addCode(l.c1);
						break;

					
					//TODO HANTERA FÖR ANY KONCEPT					
					case BRANCH:
						Branch b = (Branch) conf.getInst();
						// Pop stack to see tt or ff
						eo = conf.getEval();
						if (eo.getTT() == TTExc.TT) {
							conf.addCode(b.c1);
						} else if (eo.getTT() == TTExc.FF) {
							conf.addCode(b.c2);
						} else /*TTExc == T*/{
							//Case TT
							Configuration con1 = new Configuration(conf);
							con1.addCode(b.c1);
							con1.increaseStepCount();
							//Case FF
							Configuration con2 = new Configuration(conf);
							con2.addCode(b.c2);
							con2.increaseStepCount();
							//Add both to conf set
							confs.add(con1);
							confs.add(con2);
							return confs;
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


					case NOOP:
						//consume
						conf.getInst();
						// Do nothing.
						break;
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
		confs.add(conf);
		return confs;
	}
}
