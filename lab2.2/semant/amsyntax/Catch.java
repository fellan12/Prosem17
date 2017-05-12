package semant.amsyntax;

public class Catch extends Inst {

	public final Code c2;

    public Catch(Code c2) {
        super(Opcode.CATCH);
        this.c2 = c2;
    }

    public String toString(){
    	return super.toString() + "(" + c2 + ")";
    }


}
