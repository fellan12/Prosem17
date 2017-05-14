package semant.whilesyntax;

import semant.WhileVisitor;
import semant.amsyntax.Code;
import semant.signexc.*;


public class While extends Stm {

    public final Bexp b;
    public final Stm s;
    public TTExc ttSign;
    
    public While(Bexp b, Stm s) {
        this.b = b;
        this.s = s;
    }
    public Code accept(WhileVisitor v) {
        return v.visit(this);
    }

}
