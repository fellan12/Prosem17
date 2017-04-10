package semant.amsyntax;

public class TryC extends Inst {
	public final Code c1;
	public final Code c2;

    public TryC(Code c1, Code c2) {
        super(Opcode.TRYCATCH);
        this.c1 = c1;
        this.c2 = c2;
    }

    public String toString(){
    	return super.toString() + "(" + c1 + ", " + c2 + ")";
    }
}
