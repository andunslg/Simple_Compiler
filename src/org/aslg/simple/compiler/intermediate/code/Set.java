package org.aslg.simple.compiler.intermediate.code;

import org.aslg.simple.compiler.symbols.Type;

/**
 * Created with IntelliJ IDEA.
 * User: AndunSLG
 * Date: 3/11/14
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class Set extends Stmt {

    public Id id;
    public Expr expr;
    public Type type;
    public Set(Id i, Expr ex) {
        this.id = i;
        this.expr = ex;
        if(check(i.type, ex.type) == null){
            error ("type error narrowing not allowed!");
        }
    }
    public Type check(Type t1, Type t2){
        if(t1 == Type.max(t1, t2)){
            return t1;
        }else{return null;}

    }
    public Expr gen(){
        Expr t = expr.gen(); //generate RHS of the assignemnt (stmt) -- evaluateinternal nodes
        Expr rt = t.reduce(); // evaluate root
        type = Type.max(id.type,expr.type); // get maxmimum type of RHS and LHS
        rt = widen(rt,rt.type,type); // do widening for evaluated EHS expression
        emit(id .toString() +  " = "+ rt.toString() );
        return t;
    }

    public Expr widen(Expr a,Type t1, Type t2){
        if(t1.lexeme.equals(t2.lexeme)) return a;
        else if(t1 == Type.Int && t2 == Type.Float){
            Temp temp = (Temp)Arith.expressions.get(a.toString());
            if(temp == null){
                temp = new Temp(type);
                emit(temp.toString()+ " = (float)" + a.toString());
                Arith.expressions.put(a.toString(), temp);
                return temp;
            }
            return temp;
        }
        else error("Type mismatch");
        return null;
    }
}
