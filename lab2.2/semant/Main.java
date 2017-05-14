package semant;

import semant.whilesyntax.Stm;
import semant.amsyntax.*;
import semant.signexc.*;
import java.util.*;
import java.io.*;

public class Main {
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";

	private static boolean pressKeyToContinue(){ 

		System.out.println(ANSI_GREEN+ "Press enter key to continue or write 'skip' to go to the end or 'exit' to quit" + ANSI_RESET);
		try{
			Scanner sc = new Scanner(System.in);
			String str = sc.nextLine();
			if(str.equals("skip")){
				return false;
			}else if(str.equals("exit")){
				System.exit(0);
			}
		}  
		catch(Exception e){
		}  
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		// - Compile s into AM Code
		Stm s = WhileParser.parse(args[0]); 
		CompileVisitor comVisit = new CompileVisitor();
		Code y = s.accept(comVisit);

		HashMap<Integer,Stm> stmMap = comVisit.getStmMap();
		

		//Collect all variables
		HashSet<String> vars = new HashSet<String>();
		for (Inst i : y) {
			switch(i.opcode){
				case STORE:
					Store str = (Store) i;
					vars.add(str.x);
					break;
				case FETCH:
					Fetch fet = (Fetch) i;
					vars.add(fet.x);
					break;
			}
		}

		//Set inputed variables to Z
		Configuration conf = new Configuration(y);
		ArrayList<String> vars2 = new ArrayList<String>(vars);
		for (int i = 0; i < vars.size() ; i++ ) {
			conf.addStorage(vars2.get(i), SignExc.Z);
		}

			

		int exitPoint = 0;
		for (Inst i : y){
			if(i.getControlPoint() > exitPoint){
				switch(i.opcode){
					case BRANCH: exitPoint = i.getControlPoint()+2;
						
					default: exitPoint = i.getControlPoint()+1;
				}
			}
		}


		//Run program on VM
  		VirtualMachine VM = new VirtualMachine();
  		boolean steps = true;
  		Set<Configuration> stepConfs = new HashSet<Configuration>();
  		Set<Configuration> newConfs = new HashSet<Configuration>();
  		List<Configuration> confsGraph = new ArrayList<Configuration>();
  		Set<Integer> visitedConfs = new HashSet<Integer>();
  		stepConfs.add(conf);
  		while(!stepConfs.isEmpty()){
  			for (Configuration con : stepConfs) {
  				if(!visitedConfs.contains(con.hashCode())){
  					//Current configs to compute
	  				System.out.println("Configuration Set Size: " + stepConfs.size());
	  				//Configs that have no code left
	  				System.out.println("Config Graph Set Size " + confsGraph.size());
		 			con.printConfig();
		 			if(steps){
		 				steps = pressKeyToContinue();
		 			}
		 			visitedConfs.add(con.hashCode());
		 			confsGraph.add(new Configuration(con));
		  			newConfs.addAll(VM.step(con));

	  				
  				}else{
  					confsGraph.add(new Configuration(con));
  					visitedConfs.add(con.hashCode());
  					continue;
  				}
  				
  			}
  			stepConfs.clear();
  			stepConfs.addAll(newConfs);
  			newConfs.clear();
  			System.out.println("#######################################################################################################################################");
  			System.out.println("#######################################################################################################################################");
  		}

  		confsGraph.removeIf(c -> c.getControlPoint() == 0);
  		for (Configuration c : confsGraph) {
  			if(c.getControlPoint() > exitPoint){
  				c.setControlPoint(-1);
  			}
  			System.out.println(c.getBranchString() + " (" + c.getControlPoint() + ") " + c.getCode());
  			System.out.println(c.getState().printState());  
  			System.out.println();
  		}

  		


  		//PrettyPrinter
  		for (Integer i : stmMap.keySet()) {
  			PrettyPrinter p = new PrettyPrinter();
  			stmMap.get(i).accept(p);
  		}

	}
}