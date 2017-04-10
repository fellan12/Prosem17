package semant;

import semant.amsyntax.*;
import semant.whilesyntax.*;

public class CompileVisitor implements WhileVisitor {
    
    public Code visit(Compound compound) {
        Code c = new Code();
        c.addAll(compound.s1.accept(this));
        c.addAll(compound.s2.accept(this));
        return c;
    }
    
    public Code visit(Not not) {
        Code c = new Code();
        c.addAll(not.b.accept(this));
        c.add(new Neg());
        return c;
    }
    
    public Code visit(Conjunction and) {
        Code c = new Code();
        c.addAll(and.b2.accept(this));
        c.addAll(and.b1.accept(this));
        c.add(new And());
        return c;
    }
    
    public Code visit(Assignment assignment) {
        Code c = new Code();
        c.addAll(assignment.a.accept(this));
        c.add(new Store(assignment.x.id));
        return c;
    }
    
    public Code visit(Conditional conditional) {
        Code c = new Code();
        c.addAll(conditional.b.accept(this));
        Code c1 = new Code();
        c1.addAll(conditional.s1.accept(this));
        Code c2 = new Code();
        c2.addAll(conditional.s2.accept(this));
        c.add(new Branch(c1,c2));
        return c;
    }
    
    public Code visit(Equals equals) {
        Code c = new Code();
        c.addAll(equals.a2.accept(this));
        c.addAll(equals.a1.accept(this));
        c.add(new Eq());
        return c;
    }

    public Code visit(FalseConst f) {
        Code c = new Code();
        c.add(new False());
        return c;
    }

    public Code visit(LessThanEq lessthaneq) {
        Code c = new Code();
        c.addAll(lessthaneq.a2.accept(this));
        c.addAll(lessthaneq.a1.accept(this));
        c.add(new Le());
        return c;
    }

    public Code visit(Minus minus) {
        Code c = new Code();
        c.addAll(minus.a2.accept(this));
        c.addAll(minus.a1.accept(this));
        c.add(new Sub());
        return c;
    }

    public Code visit(Num num) {
        Code c = new Code();
        c.add(new Push(num.n));
        return c;
    }
    
    public Code visit(Plus plus) {
        Code c = new Code();
        c.addAll(plus.a2.accept(this));
        c.addAll(plus.a1.accept(this));
        c.add(new Add());
        return c;
    }

    public Code visit(Skip skip) {
        Code c = new Code();
        c.add(new Noop());
        return c;
    }

    public Code visit(Times times) {
        Code c = new Code();
        c.addAll(times.a2.accept(this));
        c.addAll(times.a1.accept(this));
        c.add(new Mult());
        return c;
    }
    
    public Code visit(TrueConst t) {
        Code c = new Code();
        c.add(new True());
        return c;
    }

    public Code visit(Var var) {
        Code c = new Code();
        c.add(new Fetch(var.id));
        return c;
    }

    public Code visit(While whyle) {
        Code c = new Code();
        Code c1 = new Code();
        c1.addAll(whyle.b.accept(this));
        Code c2 = new Code();
        c2.addAll(whyle.s.accept(this));
        c.add(new Loop(c1,c2));
        return c;
    }
    
    //TODO
    public Code visit(TryCatch trycatch) {
        return null;
    }
    
    //TODO
    public Code visit(Divide div) {
        return null;
    }
}
