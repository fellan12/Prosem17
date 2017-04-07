package semant;

import semant.whilesyntax.Stm;

public class Main {
    public static void main(String[] args) throws Exception {
        Stm s = WhileParser.parse(args[0]);
        s.accept(new PrettyPrinter());
        
        // TODO:
        // - Compile s into AM Code
        // - Execute resulting AM Code using a step-function.
    }
}
