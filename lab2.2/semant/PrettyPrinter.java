package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;
import semant.signexc.*;
import java.util.*;

public class PrettyPrinter implements WhileVisitor {
    
    String i = "";
    HashMap<Integer, ArrayList<Configuration>> confMap = Main.configMap;
    public static final String ANSI_GREEN = "\u001B[1;32m";
    public static final String ANSI_RED = "\u001B[1;31m";
    public static final String ANSI_YELLOW = "\u001B[1;33m";
    public static final String ANSI_RESET = "\u001B[0m"; 

    public String calcLub(ArrayList<Configuration> arr) {
        
        HashMap<String, SignExc> val1 = arr.get(0).getStorage();
        SignExcLattice signLat = new SignExcLattice();
        //Remove Catch for lub calc
        arr.removeIf(c -> c.getState().getExceptionalState() || c.peekInst().opcode == Inst.Opcode.CATCH);
        
        //Special handle for Loop lub calc
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).peekInst().opcode == Inst.Opcode.LOOP){
                return printLub(arr.get(i).getStorage());
            }
        }

        // Calc lub for general
        for(int k = 2; k <= arr.size(); k++) {
            HashMap<String, SignExc> val2 = arr.get(k-1).getStorage();
                for (String key : val1.keySet()) {
                    SignExc v1 = val1.get(key);
                    SignExc v2 = val2.get(key);
                    val2.put(key, signLat.lub(v1, v2));
                }
                val1 = val2;
        }
        return printLub(val1);
       
    }

    private String printLub(HashMap<String, SignExc> val1){
         StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (String s : val1.keySet()) {
            sb.append(s + "=" + val1.get(s) + ",");
        }
        String res = sb.toString().substring(0,sb.toString().length()-1) + "}";
        return res;
    }
    
    public Code visit(Conjunction and) {
        and.b1.accept(this);
        System.out.print(" & ");
        and.b2.accept(this);
        return null;
    }
    
    public Code visit(Assignment assignment) {
        System.out.println(i);
        if (!assignment.unReachable){
            System.out.print(i + calcLub(confMap.get(assignment.controlPoint)) + "  rhs:" + assignment.intSign);
            if(assignment.intSign == SignExc.ANY_A){
                System.out.println(ANSI_YELLOW+"   (Possible exception raiser!)"+ANSI_RESET);
            }else if(assignment.intSign == SignExc.ERR_A){
                System.out.println(ANSI_RED +"   (Definitiv exception raiser!)" +ANSI_RESET);
            }else{
                System.out.println();
            }
        }else{
            System.out.println("   (Unreachable code)");
        }
        assignment.x.accept(this);
        System.out.print(" := ");
        assignment.a.accept(this);
        return null;
    }
    
    public Code visit(Compound compound) {
        compound.s1.accept(this);
        System.out.print(";");
        compound.s2.accept(this);
        return null;
    }
    
    public Code visit(Conditional conditional) {
        System.out.println();
        if (!conditional.unReachable){
            System.out.println(calcLub(confMap.get(conditional.controlPoint)));
        }else{
            System.out.println("   (Ureachable code)");
        }        
        System.out.print(i + "if ");
        conditional.b.accept(this);
        System.out.print(" then");
        indent();
        conditional.s1.accept(this);
        outdent();
        System.out.println();
        System.out.print(i + "else");
        indent();
        conditional.s2.accept(this);
        outdent();
        return null;
    }
    
    public Code visit(Equals equals) {
        equals.a1.accept(this);
        System.out.print(" = ");
        equals.a2.accept(this);
        return null;
    }
    
    public Code visit(FalseConst f) {
        System.out.print("false");
        return null;
    }
    
    public Code visit(LessThanEq leq) {
        leq.a1.accept(this);
        System.out.print(" <= ");
        leq.a2.accept(this);
        return null;
    }
    
    public Code visit(Minus minus) {
        System.out.print("(");
        minus.a1.accept(this);
        System.out.print(" - ");
        minus.a2.accept(this);
        System.out.print(")");
        return null;
    }
    
    public Code visit(Not not) {
        System.out.print("!(");
        not.b.accept(this);
        System.out.print(")");
        return null;
    }
    
    public Code visit(Num num) {
        System.out.print(num.n);
        return null;
    }
    
    public Code visit(Plus plus) {
        System.out.print("(");
        plus.a1.accept(this);
        System.out.print(" + ");
        plus.a2.accept(this);
        System.out.print(")");
        return null;
    }
    
    public Code visit(Skip skip) {
        System.out.println();
        System.out.print(i + "skip");
        return null;
    }
    
    public Code visit(Times times) {
        System.out.print("(");
        times.a1.accept(this);
        System.out.print(" * ");
        times.a2.accept(this);
        System.out.print(")");
        return null;
    }
    
    public Code visit(TrueConst t) {
        System.out.print("true");
        return null;
    }
    
    public Code visit(Var var) {
        System.out.print(i+var.id);
        return null;
    }
    
    public Code visit(While whyle) {
        System.out.println();
        if (!whyle.unReachable){
            System.out.println(calcLub(confMap.get(whyle.controlPoint)));
        }else{
            System.out.println("    (Ureachable code)");
        }          System.out.print(i + "while ");
        whyle.b.accept(this);
        System.out.print(" do");
        indent();
        whyle.s.accept(this);
        outdent();
        return null;
    }
    
    public Code visit(Divide div) {
        System.out.print("(");
        div.a1.accept(this);
        System.out.print(" / ");
        div.a2.accept(this);
        System.out.print(")");
        return null;
    }

    public Code visit(TryCatch trycatch) {
        System.out.println();
        if (!trycatch.unReachable){
            System.out.println(calcLub(confMap.get(trycatch.controlPoint)));
        }else{
            System.out.println("    (Unreachable code)");
        }         
        System.out.print(i + "try");
        indent();
        trycatch.s1.accept(this);
        outdent();
        System.out.println();
        System.out.print(i + "catch");
        indent();
        trycatch.s2.accept(this);
        outdent();
        return null;
    }

    private void indent() {
        i += "    ";
    }
    
    private void outdent() {
        i = i.substring(4);
    }
}
