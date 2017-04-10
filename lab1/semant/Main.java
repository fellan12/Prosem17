package semant;

import semant.whilesyntax.Stm;
import semant.amsyntax.*;

public class Main {
	private static void pressKeyToContinue(){ 
		System.out.println("Press any key to continue...");
		try{
			System.in.read();
		}  
		catch(Exception e){
		}  
	}
	
	public static void main(String[] args) throws Exception {
		// - Compile s into AM Code
		Stm s = WhileParser.parse(args[0]);
		Code y = s.accept(new CompileVisitor());
		
		Configuration conf = new Configuration(y);
		VirtualMachine VM = new VirtualMachine();
		while(conf.hasNext()){
			conf.printConfig();
			pressKeyToContinue();
			conf = VM.step(conf);
		}
		conf.printConfig();

        // - Execute resulting AM Code using a step-function. */
	}
}
