package semant.whilesyntax;

import semant.WhileVisitor;
import semant.amsyntax.Code;

public class Times extends Aexp {
    
    public final Aexp a1, a2;
    
    public Times(Aexp a1, Aexp a2) {
        this.a1 = a1;
        this.a2 = a2;
    }
    
    public Code accept(WhileVisitor v) {
        return v.visit(this);
    }
}
