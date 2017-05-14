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
	int branchCount = 1;				

	/*
	* Computes one step from the configurations
	*/
	public Set<Configuration> step(Configuration conf) throws CloneNotSupportedException{
		Set<Configuration> confs = new HashSet<Configuration>();
		if(conf.hasNext()){
			EvalObj eo;
			SignExc a1, a2, n;
			Code c1,c2;
			Configuration con1,con2,con3;
			System.out.println("ControlPoint: " + conf.getControlPoint());
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
						switch(ops.add(a1,a2)){
							case ANY_A:
								//Case Z
								con1 = new Configuration(conf);
								eo = new EvalObj(SignExc.Z);
								con1.addEval(eo);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case ERR
								con2 = new Configuration(conf);
								eo = new EvalObj(SignExc.ERR_A);
								con2.addEval(eo);
								con2.increaseStepCount();
								con2.getState().setExceptionalState(true);
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_A:
								conf.getState().setExceptionalState(true);
								eo = new EvalObj(SignExc.ERR_A);
								conf.addEval(eo);
								break;

							case NONE_A:
								eo = new EvalObj(SignExc.NONE_A);
								conf.addEval(eo);
								break;

							default:
								eo = new EvalObj(ops.add(a1,a2));
								conf.addEval(eo);
								break;

						}
						break;


					case SUB:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add diff to stack
						switch(ops.subtract(a1,a2)){
							case ANY_A:
								//Case Z
								con1 = new Configuration(conf);
								eo = new EvalObj(SignExc.Z);
								con1.addEval(eo);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case ERR
								con2 = new Configuration(conf);
								eo = new EvalObj(SignExc.ERR_A);
								con2.addEval(eo);
								con2.increaseStepCount();
								con2.getState().setExceptionalState(true);
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_A:
								conf.getState().setExceptionalState(true);
								eo = new EvalObj(SignExc.ERR_A);
								conf.addEval(eo);
								break;

							case NONE_A:
								eo = new EvalObj(SignExc.NONE_A);
								conf.addEval(eo);
								break;

							default:
								eo = new EvalObj(ops.subtract(a1,a2));
								conf.addEval(eo);
								break;

						}
						break;


					case MULT:
						//consume
						conf.getInst(); 
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add product to stack
						switch(ops.multiply(a1,a2)){
							case ANY_A:
								//Case Z
								con1 = new Configuration(conf);
								eo = new EvalObj(SignExc.Z);
								con1.addEval(eo);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case ERR
								con2 = new Configuration(conf);
								eo = new EvalObj(SignExc.ERR_A);
								con2.addEval(eo);
								con2.increaseStepCount();
								con2.getState().setExceptionalState(true);
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_A:
								conf.getState().setExceptionalState(true);
								eo = new EvalObj(SignExc.ERR_A);
								conf.addEval(eo);
								break;

							case NONE_A:
								eo = new EvalObj(SignExc.NONE_A);
								conf.addEval(eo);
								break;

							default:
								eo = new EvalObj(ops.multiply(a1,a2));
								conf.addEval(eo);
								break;

						}
						break;


					case DIV:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();

						switch(ops.divide(a1,a2)){
							case ANY_A:
								//Case Z
								con1 = new Configuration(conf);
								eo = new EvalObj(SignExc.Z);
								con1.addEval(eo);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case ERR
								con2 = new Configuration(conf);
								eo = new EvalObj(SignExc.ERR_A);
								con2.addEval(eo);
								con2.increaseStepCount();
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_A:
								eo = new EvalObj(SignExc.ERR_A);
								conf.addEval(eo);
								break;

							case NONE_A:
								eo = new EvalObj(SignExc.NONE_A);
								conf.addEval(eo);
								break;

							default:
								eo = new EvalObj(ops.divide(a1,a2));
								conf.addEval(eo);
								break;

						}
						break;
					

					case AND:
						//consume
						conf.getInst();
						// Get lhs
						TTExc b1 = conf.getEval().getTT();
						// Get rhs
						TTExc b2 = conf.getEval().getTT();
						// add res to stack
						switch(ops.and(b1,b2)){
							case ANY_B:
								//Case Z
								con1 = new Configuration(conf);
								eo = new EvalObj(TTExc.T);
								con1.addEval(eo);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case ERR
								con2 = new Configuration(conf);
								eo = new EvalObj(TTExc.ERR_B);
								con2.addEval(eo);
								con2.increaseStepCount();
								con2.getState().setExceptionalState(true);
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_B:
								conf.getState().setExceptionalState(true);
								eo = new EvalObj(TTExc.ERR_B);
								conf.addEval(eo);
								break;

							case NONE_B:
								eo = new EvalObj(TTExc.NONE_B);
								conf.addEval(eo);
								break;

							default:
								eo = new EvalObj(ops.and(b1,b2));
								conf.addEval(eo);
								break;

						}
						break;
					

					case EQ:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add res to stack
						switch(ops.eq(a1,a2)){
							case ANY_B:
								//Case Z
								con1 = new Configuration(conf);
								eo = new EvalObj(TTExc.T);
								con1.addEval(eo);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case ERR
								con2 = new Configuration(conf);
								eo = new EvalObj(TTExc.ERR_B);
								con2.addEval(eo);
								con2.increaseStepCount();
								con2.getState().setExceptionalState(true);
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_B:
								conf.getState().setExceptionalState(true);
								eo = new EvalObj(TTExc.ERR_B);
								conf.addEval(eo);
								break;

							case NONE_B:
								eo = new EvalObj(TTExc.NONE_B);
								conf.addEval(eo);
								break;

							default:
								eo = new EvalObj(ops.eq(a1,a2));
								conf.addEval(eo);
								break;

						}
						break;


					case LE:
						//consume
						conf.getInst();
						// Get lhs
						a1 = conf.getEval().getSign();
						// Get rhs
						a2 = conf.getEval().getSign();
						// add res to stack
						switch(ops.leq(a1,a2)){
							case ANY_B:
								//Case Z
								con1 = new Configuration(conf);
								eo = new EvalObj(TTExc.T);
								con1.addEval(eo);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case ERR
								con2 = new Configuration(conf);
								eo = new EvalObj(TTExc.ERR_B);
								con2.addEval(eo);
								con2.increaseStepCount();
								con2.getState().setExceptionalState(true);
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_B:
								conf.getState().setExceptionalState(true);
								eo = new EvalObj(TTExc.ERR_B);
								conf.addEval(eo);
								break;

							case NONE_B:
								eo = new EvalObj(TTExc.NONE_B);
								conf.addEval(eo);
								break;

							default:
								eo = new EvalObj(ops.leq(a1,a2));
								conf.addEval(eo);
								break;

						}
						break;


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
						TTExc tt = conf.getEval().getTT();
						switch(ops.neg(tt)){
							case ANY_B:
								//Case Z
								con1 = new Configuration(conf);
								eo = new EvalObj(TTExc.T);
								con1.addEval(eo);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case ERR
								con2 = new Configuration(conf);
								eo = new EvalObj(TTExc.ERR_B);
								con2.addEval(eo);
								con2.increaseStepCount();
								con2.getState().setExceptionalState(true);
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_B:
								conf.getState().setExceptionalState(true);
								eo = new EvalObj(TTExc.ERR_B);
								conf.addEval(eo);
								break;

							case NONE_B:
								eo = new EvalObj(TTExc.NONE_B);
								conf.addEval(eo);
								break;

							default:
								eo = new EvalObj(ops.neg(tt));
								conf.addEval(eo);
								break;
						}
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
						if(n == SignExc.ERR_A){
							conf.getState().setExceptionalState(true);
						}else if(n == SignExc.ANY_A){
							//Case Z
							con1 = new Configuration(conf);
							con1.addStorage(x, SignExc.Z);
							con1.increaseStepCount();
							con1.setControlPoint(conf.getControlPoint());
							//Case ERR
							con2 = new Configuration(conf);
							con2.addStorage(x, SignExc.ERR_A);
							con2.increaseStepCount();
							con2.getState().setExceptionalState(true);
							branchCount = conf.getBranch()+1;
							con2.setBranch(branchCount);
							con2.setControlPoint(conf.getControlPoint());
							//Add both to conf set
							confs.add(con1);
							confs.add(con2);
							return confs;
						}else{
							conf.addStorage(x, n);
							break;	
						}
											


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

						switch(eo.getTT()){
							case TT:
								conf.addCode(b.c1);
								break;

							case FF:
								conf.addCode(b.c2);
								break;

							case T:
								//Case TT
								con1 = new Configuration(conf);
								con1.addCode(b.c1);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case FF
								con2 = new Configuration(conf);
								con2.addCode(b.c2);
								con2.increaseStepCount();
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								return confs;

							case ERR_B:
								conf.getState().setExceptionalState(true);
								break;

							case NONE_B:
								//Nothing jump over
								break;

							case ANY_B:
								//Case T
								//Case TT
								con1 = new Configuration(conf);
								con1.addCode(b.c1);
								con1.increaseStepCount();
								con1.setControlPoint(conf.getControlPoint());
								//Case FF
								con2 = new Configuration(conf);
								con2.addCode(b.c2);
								con2.increaseStepCount();
								branchCount = conf.getBranch();
								branchCount = conf.getBranch()+1;
								con2.setBranch(branchCount);
								con2.setControlPoint(conf.getControlPoint());							
								//Add both to conf set
								confs.add(con1);
								confs.add(con2);
								//Case ERR_B
								con3 = new Configuration(conf);
								con3.getState().setExceptionalState(true);
								con3.increaseStepCount();
								branchCount = conf.getBranch()+1;
								con3.setBranch(branchCount);
								con3.setControlPoint(conf.getControlPoint());
								//Add all to confs set
								confs.add(con1);
								confs.add(con2);
								confs.add(con3);
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
					conf.getEvalStack().clear();
					conf.getInst();

				}
			}
			
		}
		
		conf.increaseStepCount();
		confs.add(conf);
		return confs;
	}
}
