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
        c.addAll(and.b1.accept(this));
        c.addAll(and.b2.accept(this));
        c.add(new And())
        return c;
    }
    
    public Code visit(Assignment assignment) {
        Code c = new Code();
        c.addAll(assignment.a.accept(this));
        c.add(new Store(assignment.x.accept(this)));
        return c;
    }
    
    public Code visit(Conditional conditional) {
        Code c = new Code();
        c.addAll(conditional.b.accept);
        Code c1 = new Code();
        c1.addAll(conditional.s1.accept(this));
        Code c2 = new Code();
        c2.addAll(conditional.s2.accept(this));
        c.add(new Branch(c1,c2));
        return c;
    }
    
    public Code visit(Equals equals) {
        Code c = new Code();
        c.addAll(and.a1.accept(this));
        c.addAll(and.a2.accept(this));
        c.add(new Eq());
        return c;
    }

    public Code visit(FalseConst f) {
        return new Code().add(new False());
    }

    public Code visit(LessThanEq lessthaneq) {
        Code c = new Code();
        c.addAll(and.a1.accept(this));
        c.addAll(and.a2.accept(this));
        c.add(new Le())
        return c;
    }

    public Code visit(Minus minus) {
        Code c = new Code();
        c.addAll(and.a1.accept(this));
        c.addAll(and.a2.accept(this));
        c.add(new Sub());
        return c;
    }

    public Code visit(Num num) {
        return new Code().add(new Push(num.n));
    }
    
    public Code visit(Plus plus) {
        Code c = new Code();
        c.addAll(and.a1.accept(this));
        c.addAll(and.a2.accept(this));
        c.add(new Add());
        return c;
    }

    public Code visit(Skip skip) {
        return new Code().add(new Noop());
    }

    public Code visit(Times times) {
        Code c = new Code();
        c.addAll(and.a1.accept(this));
        c.addAll(and.a2.accept(this));
        c.add(new Mul());
        return c;
    }
    
    public Code visit(TrueConst t) {
        return new Code().add(new True());
    }

    public Code visit(Var var) {
        return new Code().add(new Fetch(var.id));
    }

    public Code visit(While whyle) {
        Code c = new Code();
        Code c1 = new Code();
        c1.addAll(whyle.b.accept(this));
        Code c2 = new Code();
        c2.addAll(whyle.s.accept(this));
        c.add(new Loop(c1,c2))
        return c;
    }
    
    public Code visit(TryCatch trycatch) {
        return null;
    }
    
    public Code visit(Divide div) {
        return null;
    }
}
