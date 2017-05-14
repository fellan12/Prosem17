package semant.whilesyntax;

import semant.WhileVisitor;
import semant.amsyntax.Code;

public abstract class Stm {
    public abstract Code accept(WhileVisitor v);
    public int controlPoint = -1;
    public boolean unReachable = false;
    public boolean possibleExcetionRaiser = false;
}
