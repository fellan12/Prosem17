package semant;

import semant.whilesyntax.Stm;
import semant.amsyntax.*;
import java.util.*;

public class Main {
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";

	private static boolean pressKeyToContinue(){ 

		System.out.println(ANSI_GREEN+ "Press enter key to continue or write 'skip' to go to the end" + ANSI_RESET);
		try{
			Scanner sc = new Scanner(System.in);
			if(sc.nextLine().equals("skip")){
				return false;
			}
		}  
		catch(Exception e){
		}  
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		// - Compile s into AM Code
		Stm s = WhileParser.parse(args[0]);
		Code y = s.accept(new CompileVisitor());
		
		Configuration conf = new Configuration(y);
		VirtualMachine VM = new VirtualMachine();
		boolean steps = true;
		while(conf.hasNext()){
			conf.printConfig();
			if(steps){
				steps = pressKeyToContinue();
			}
			conf = VM.step(conf);
		}
		conf.printConfig();

        // - Execute resulting AM Code using a step-function. */
	}
}
