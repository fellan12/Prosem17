package semant.whilesyntax;

import semant.WhileVisitor;
import semant.amsyntax.Code;
import semant.signexc.*;
import java.util.*;

public class Assignment extends Stm {

    public final Var x;
    public final Aexp a;
    public SignExc intSign = SignExc.NONE_A;
    public SignExc rhs;
    
    public Assignment(Var x, Aexp a) {
        this.x = x;
        this.a = a;
    }
    
    public Code accept(WhileVisitor v) {
        return v.visit(this);
    }
}
