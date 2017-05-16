package semant;

import semant.whilesyntax.Stm;
import semant.amsyntax.*;
import semant.signexc.*;
import java.util.*;
import java.io.*;

public class Main {
	public static final String ANSI_GREEN = "\u001B[1;32m";
	public static final String ANSI_RED = "\u001B[1;31m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static HashMap<Integer,Stm> stmMap;
	public static ArrayList<Integer> controlPointList;
	public static HashMap<Integer, ArrayList<Configuration>> configMap = new HashMap<Integer, ArrayList<Configuration>>();


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

		stmMap = comVisit.getStmMap();
		controlPointList = comVisit.getControlPoints();		

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
		//System.out.println("start" + conf.getControlPoint());
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
	  				if(steps){
	  					//Current configs to compute
		  				System.out.println("Configuration Set Size: " + stepConfs.size());
		  				//Configs that have no code left
		  				System.out.println("Config Graph Set Size " + confsGraph.size());
			 			con.printConfig();
			 			System.out.println("#######################################################################################################################################");
  						System.out.println("#######################################################################################################################################");
		 				steps = pressKeyToContinue();
		 			}
		 			visitedConfs.add(con.hashCode());
  					confsGraph.add(new Configuration(con));
		  			newConfs.addAll(VM.step(con));
  				}else{
  					visitedConfs.add(con.hashCode());
  					confsGraph.add(new Configuration(con));
  					continue;
  				}
  				
  			}
  			stepConfs.clear();
  			stepConfs.addAll(newConfs);
  			newConfs.clear();
  		}


  		//Set end point to -1
  		for (Configuration c : confsGraph) {
  			if(c.getCode().size() == 0){
  				c.setControlPoint(-2);
  			}
  		}
  		


  		//ArrayList<ArrayList<Configuration>> res = new ArrayList<ArrayList<Configuration>>();
  		// calculate lub
  		for (Configuration c : confsGraph) {
  			if(configMap.get(c.getControlPoint()) == null){
  				configMap.put(c.getControlPoint(), new ArrayList<Configuration>());
  				//System.out.println("Added "+c.getControlPoint()+ " - " +c.getCode());
  				configMap.get(c.getControlPoint()).add(c);	
  			}else{
  				//System.out.println("Added "+c.getControlPoint()+ " - " +c.getCode());
  				configMap.get(c.getControlPoint()).add(c);	
  			}
  		}


  		// find unreachable code
  		boolean noPoint = false;
  		for (Integer i : controlPointList) {
  			for(Configuration c : confsGraph){
  				if(i == c.getControlPoint() && !c.getState().getExceptionalState()){
  					noPoint = true;
  				}
  			}
  			if(!noPoint){
  				stmMap.get(i).unReachable = true;
  			}
  			noPoint = false;
  		}  	
  		
  	  	//Final lub
  	  	try{
	  	  	SignExcLattice signLat = new SignExcLattice();
	 		ArrayList<Configuration> lastConfs = configMap.get(-1);
			HashMap<String, SignExc> val1 = lastConfs.get(0).getStorage();
			for(int k = 2; k <= lastConfs.size(); k++) {
				HashMap<String, SignExc> val2 = lastConfs.get(k-1).getStorage();

				for (String key : val1.keySet()) {
					SignExc v1 = val1.get(key);
					SignExc v2 = val2.get(key);
					val2.put(key, signLat.lub(v1, v2));
				}
				val1 = val2;
			}

			//PrettyPrint
	 		PrettyPrinter p = new PrettyPrinter();
	  	  	s.accept(p);

			StringBuilder sb = new StringBuilder();
	 		sb.append("{");
	 		for (String str : val1.keySet()) {
	 			 sb.append(str + "=" + val1.get(str) + ",");
	 		}
	 		String res = sb.toString().substring(0,sb.toString().length()-1);
	 		System.out.println("\n"+ res + "}" + ANSI_GREEN + "    (normal termination)" + ANSI_RESET);
		}catch(NullPointerException e){
			//PrettyPrint
	 		PrettyPrinter p = new PrettyPrinter();
	  	  	s.accept(p);

	  	  	System.out.println(ANSI_RED+"\n(Infinite Loop)"+ ANSI_RESET);
		}

		
	}
}
