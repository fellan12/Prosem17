package semant;

import semant.whilesyntax.Stm;
import semant.amsyntax.*;
import java.util.*;

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
		System.out.println(s);
		Code y = s.accept(new CompileVisitor());
		System.out.println(y);
			
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

		//Add inputed variables if wanted
		Configuration conf = new Configuration(y);
		ArrayList<String> vars2 = new ArrayList<String>(vars);
		System.out.println("Do you want to assign values to the varaibles? ");
		System.out.println("Press enter to skip to the next variable");
		if(pressKeyToContinue()){
			Scanner scan = new Scanner(System.in);
			for (int i = 0; i < vars.size() ; i++ ) {
				System.out.print(vars2.get(i) + " = ");
				try{
				conf.addStorage(vars2.get(i), Integer.parseInt(scan.nextLine()));
				}catch (Exception e){
					System.out.println("Skipped " + vars2.get(i));
				}
			}
		}
		

		//Run program on VM
		VirtualMachine VM = new VirtualMachine();
		boolean steps = true;
		Set<Configuration> confs1 = new Set<Configuration>();
		Set<Configuration> confs2 = new Set<Configuration>();
		confs1.add(conf);
		Iterator it = confs1.interator();
		while(it.hasNext()){
			conf = it.next();
			if(conf.hasNext()){
				conf.printConfig();
				if(steps){
					steps = pressKeyToContinue();
				}
				conf2.addall(VM.step(conf));
			}
		}
		
		conf.printConfig();
		
	}

	public void steep(Set<Configuration> confs) {
		Iterator it = confs.interator();
		while(it.hasNext()){
			conf = it.next();
			while(conf.hasNext()){
				conf.printConfig();
				if(steps){
					steps = pressKeyToContinue();
				}
				steep(VM.step(conf));
			}
		}
	}
}
