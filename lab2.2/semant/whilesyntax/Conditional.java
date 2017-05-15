package semant.whilesyntax;

import semant.WhileVisitor;
import semant.amsyntax.Code;
import semant.signexc.*;
import java.util.*;


public class Conditional extends Stm {

    public final Bexp b;
    public final Stm s1, s2;
    public TTExc ttSign = TTExc.NONE_B;
    
    public Conditional(Bexp b, Stm s1, Stm s2) {
        this.b = b;
        this.s1 = s1;
        this.s2 = s2;
    }
    
    public Code accept(WhileVisitor v) {
        return v.visit(this);
    }
}
