package semant;

import semant.whilesyntax.Stm;
import semant.amsyntax.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Stm s = WhileParser.parse(args[0]);
        Code y = s.accept(new CompileVisitor());
        for (Inst i : y) {
        	System.out.println(i);
        }

        
        // TODO:
        // - Compile s into AM Code
        // - Execute resulting AM Code using a step-function.
    }
}
